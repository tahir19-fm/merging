package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.taomish.app.android.farmsanta.farmer.BuildConfig
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_scouting_image_details.AddScoutingImageDetailsFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryItem
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.ScoutImage
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Weather
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.models.api.profile.UploadedFile
import com.taomish.app.android.farmsanta.farmer.models.api.weather.Current
import com.taomish.app.android.farmsanta.farmer.utils.add
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.saveFileInStorage
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import java.io.File
import java.io.IOException

private const val FILE_NAME = "farm-scout"

@Suppress("UNCHECKED_CAST")
class ScoutingImageDetailsFromHome : FarmSantaBaseFragment() {

    private var mFarmScouting: FarmScouting? = null
    private var cropCategoryList: List<CategoryItem?>? = null

    private lateinit var mUrlPath: String

    private var mContext: Context? = null

    private var addedCrop: String? = null
    private lateinit var photoFile: File
    private lateinit var selectedFarmer: Farmer
    private val viewModel by viewModels<FarmScoutingViewModel>()

    val uploadedImages = mutableListOf<ScoutImage>()


    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                openCamera()
            }
        }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                mContext?.showToast(R.string.permission_granted_msg)
                openGallery()
            } else {
                mContext?.showToast(R.string.permission_denied)
            }
        }


    private val takePicResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.imageFiles.postValue(viewModel.imageFiles.value + photoFile.path)
        }
    }


    private val galleryPicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                saveFileInStorage(
                    context = this.requireContext(),
                    uri = uri
                )?.let { viewModel.imageFiles.add(it.path) }
            }
        }


    override fun init() {
        mUrlPath = ScoutingImageDetailsFromHomeArgs.fromBundle(requireArguments()).filePath
    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?,
    ) = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    AddScoutingImageDetailsFragmentScreen(
                        viewModel = viewModel,
                        onGallery = this@ScoutingImageDetailsFromHome::takeGalleryPic,
                        onDone = {
                            if (viewModel.validate(mContext!!)) {
                                if (viewModel.imageFiles.value.isNotEmpty()) {
                                    uploadFarmScout(File(viewModel.imageFiles.value.first()), 1)
                                } else createFarmScouting()
                            }
                        }
                    )
                }
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cameraPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    openCamera()
                }
            }

        viewModel.captureImage.observe(viewLifecycleOwner) {
            if (SDK_INT >= M && SDK_INT <= Build.VERSION_CODES.P) {
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            } else {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile()
        val fileProvider = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID.plus(".provider"),
            photoFile
        )
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

        if (SDK_INT >= M) {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            } else {
                takePicResultLauncher.launch(cameraIntent)
            }
        } else {
            takePicResultLauncher.launch(cameraIntent)
        }
    }

    private fun openGallery() {
        galleryPicker.launch("image/*")
    }

    private fun takeGalleryPic() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            permissions.plus(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }*/
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions13 = arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            if (permissions13.all {
                    ActivityCompat.checkSelfPermission(requireActivity(), it) !=
                            PackageManager.PERMISSION_GRANTED
                }
            ) {
                requestGalleryPermission.launch(permissions)
            } else {
                openGallery()
            }
        } else if (SDK_INT >= M) {
            if (permissions.all {
                    ActivityCompat.checkSelfPermission(requireActivity(), it) !=
                            PackageManager.PERMISSION_GRANTED
                }
            ) {
                requestGalleryPermission.launch(permissions)
            } else {
                openGallery()
            }
        } else {
            openGallery()
        }
    }

    private fun getPhotoFile(): File {
        val storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(FILE_NAME, ".jpg", storageDirectory)
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        DataHolder.getInstance().selectedFarmer?.let {
            selectedFarmer = it
        }
        addedCrop = ""
        // API for crops and crop stage
        fetchCropList()
        viewModel.selectedCropDto.observe(viewLifecycleOwner) {
            fetchCropStageList(it?.uuid ?: "")
        }
        fetchCropCategories()
        getPlantPart()
    }


    private fun fetchCropCategories() {
        if (cropCategoryList.isNullOrEmpty()) {
            val task = GetCropCategoryTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is List<*>) {
                        cropCategoryList = data as List<CategoryItem?>
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {

                }

            })
            task.execute()
        }
    }

    /*Get Crop List API*/
    private fun fetchCropList() {

        DataHolder.getInstance().cropArrayList?.let { crops ->
            viewModel.cropDtoList.addAll(crops)
            crops.forEach {
                it.cropName?.let { name ->
                    viewModel.cropList.add(name)
                    DataHolder.getInstance().dataObject?.let { disease ->
                        if (disease is Disease) {
                            disease.crops?.getOrNull(0)?.let {

                            }
                        }
                    }
                }
            }
            return
        }

        val task = GetCropListTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val list = data as ArrayList<CropMaster>?
                    DataHolder.getInstance().cropArrayList = list
                    viewModel.cropDtoList.addAll(data)
                    val map = mutableMapOf<String, CropMaster>()
                    list?.forEach {
                        map[it.uuid] = it
                        it.cropName?.let { name -> viewModel.cropList.add(name) }
                    }
                    viewModel.crops.postValue(map)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }

    @Throws(IOException::class)
    private fun uploadFarmScout(file: File, index: Int) {
        val task = SaveFarmScoutImageTask(file)
        task.context = mContext
        task.setLoadingMessage(getString(R.string.loading_farm_scouting_msg))
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                val savedFileName = (data as UploadedFile).fileName
                val img = ScoutImage()
                img.image = savedFileName
                img.plantPart = viewModel.selectedPlantDto.value?.name ?: ""
                img.status = "active"
                img.category = null
                img.label = savedFileName
                uploadedImages.add(img)
                if (index < viewModel.imageFiles.value.size) {
                    val newIndex = index + 1
                    uploadFarmScout(File(viewModel.imageFiles.value[index]), newIndex)
                } else {
                    createFarmScouting()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason: $reason\nmessage: $errorMessage")
            }
        })
        task.execute()
    }

    private fun goToQuerySent() {
        fragmentChangeHelper?.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_QUERY_SENT)
    }

    private fun createFarmScouting() {
        //get  file name after upload
        val weatherAll = DataHolder.getInstance().weatherAll
        val currentWeather: Current? = weatherAll?.weatherDetails?.current
        val weather = Weather(
            System.currentTimeMillis(),
            currentWeather?.temp ?: 0.0,
            DataHolder.getInstance().lastKnownLocation?.longitude?.toFloat() ?: 360.0f,
            DataHolder.getInstance().lastKnownLocation?.latitude?.toFloat() ?: 360.0f,
            currentWeather?.pressure ?: 0.0,
            currentWeather?.humidity ?: 0.0,
            currentWeather?.windSpeed ?: 0.0,
            currentWeather?.uvi ?: 0.0,
            currentWeather?.clouds ?: 0.0,
            currentWeather?.visibility ?: 0.0,
            currentWeather?.rain?.toString() ?: "0"
        )

        // save crop name and stage name to farm scouting api
        mFarmScouting = FarmScouting(
            caption = viewModel.queryText.value,
            landId = "",
            images = uploadedImages,
            crop = viewModel.selectedCropDto.value?.uuid ?: "",
            cropStage = viewModel.selectedStageDto.value?.uuid ?: "",
            currentWeather = weather,
            createdBy = selectedFarmer.uuid,
            farmerId = selectedFarmer.uuid,
            region = selectedFarmer.region ?: emptyList(),
            territory = selectedFarmer.territory ?: emptyList()
        )
        saveFarmScoutingService()
    }

    private fun saveFarmScoutingService() {
        mFarmScouting?.let {
            val task = SaveFarmScoutingTask(it)
            task.context = mContext
            task.isCancelable = false
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) = goToQuerySent()

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }

    }


    private fun fetchCropStageList(cropId: String = "") {
        val task = GetCropStageListByCropId(cropId)
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Array<*>).also {
                    if (it.isNotEmpty()) {
                        viewModel.growthStageDtoList.clear()
                        viewModel.growthStageList.clear()
                        viewModel.growthStageDtoList.addAll(it.toMutableList() as ArrayList<CropStage>)
                        val map = mutableMapOf<String, CropStage>()
                        viewModel.growthStageDtoList.forEach { stage ->
                            map[stage.uuid] = stage
                            stage.name?.let { name -> viewModel.growthStageList.add(name) }
                        }
                        viewModel.stages.postValue(map)
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }

    private fun getPlantPart() {
        viewModel.plantPartDtoList.clear()
        viewModel.plantList.clear()
        val task = GetPlantPartTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data as ArrayList<GlobalIndicatorDTO>
                    viewModel.plantPartDtoList.addAll(list)
                    viewModel.plantList.addAll(list.map { it.name ?: "" })
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }
        })
        task.execute()
    }
}