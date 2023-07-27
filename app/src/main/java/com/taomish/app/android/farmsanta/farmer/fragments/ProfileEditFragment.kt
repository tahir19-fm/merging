package com.taomish.app.android.farmsanta.farmer.fragments


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.ProfileEditFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.StartActivityConstants.CAMERA_REQUEST
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.StartActivityConstants.GALLERY_REQUEST
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.fragments.dialog.FarmSantaDatePickerDialog
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.gender.GenderItem
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.master.SubCounty
import com.taomish.app.android.farmsanta.farmer.models.api.master.UOMType
import com.taomish.app.android.farmsanta.farmer.models.api.master.Village
import com.taomish.app.android.farmsanta.farmer.utils.*
import java.io.IOException


@Suppress("UNCHECKED_CAST")
class ProfileEditFragment : FarmSantaBaseFragment() {

    private lateinit var currentFarmer: Farmer
    private val viewModel: SignUpAndEditProfileViewModel by activityViewModels()
    private var ageOfPerson = 18
    private lateinit var mContext: Context
    private var permissionRequest = -1


    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                mContext.showToast(R.string.permission_granted_msg)
                when(permissionRequest) {
                    GALLERY_REQUEST -> openGallery()
                    CAMERA_REQUEST -> openCamera()
                    else -> Unit
                }
            } else {
                mContext.showToast(R.string.permission_denied)
            }
        }


    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.profileBitmap.postValue(uri.asBitmap(requireContext()))
            }
        }

    private val captureFromCamera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
            if (result != null) {
                viewModel.profileBitmap.postValue(result)
            } else {
                mContext.showToast("failed to capture image")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    ProfileEditFragmentScreen(
                        viewModel = viewModel,
                        openDatePickerDialog = openDatePickerDialog,
                        goToAddCrops = { goToAddCrops() },
                        goToAddOrEditFarmLocation = this@ProfileEditFragment::goToAddFarmLocation,
                        onSelectCamera = this@ProfileEditFragment::takePicture,
                        onSelectGallery = this@ProfileEditFragment::takeGalleryPic,
                        onDone = {
                            if (viewModel.validateFarmer(requireContext())) {
                                setUserDetails()
                            }
                        }
                    )
                }
            }
        }
        return root
    }

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {

    }

    override fun initListeners() {

    }

    override fun initData() {
        mContext = requireContext()
        if (DataHolder.getInstance().selectedFarmer != null) {
            currentFarmer = DataHolder.getInstance().selectedFarmer
        } else {
            getCurrentUser()
        }
        viewModel.selectedFarmer.postValue(currentFarmer)
        viewModel.appPrefs = AppPrefs(mContext)
        viewModel.loadFarmerDetails(mContext)
        fetchAllSubCounties()
        fetchAllVillages()
        DataHolder.getInstance().myCrops.forEach { crop ->
            if (viewModel.allSelectedCrops.find { it.uuid == crop.uuid } == null) {
                viewModel.allSelectedCrops.add(crop)
            }
        }
        fetchEducationLevels()
        fetchGender()
        fetchRegionList()
        fetchLandUOMs()
    }


    private fun takePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission.launch(arrayOf(Manifest.permission.CAMERA))
                permissionRequest = CAMERA_REQUEST
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    private fun takeGalleryPic() {
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissions.all {
                    ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        it
                    ) != PackageManager.PERMISSION_GRANTED
                }) {
                requestPermission.launch(permissions)
                permissionRequest = GALLERY_REQUEST
            } else {
                openGallery()
            }
        } else {
            openGallery()
        }
    }

    private fun openCamera() {
        captureFromCamera.launch()
    }

    private fun openGallery() {
        takePhoto.launch("image/*")
    }


    /**
     * fetching all subCounties storing in DataHolder and passing subDistrict name of farmer to get
     * farmer's subCounty Object
     */
    private fun fetchAllSubCounties() {
        val task = GetAllSubCountiesTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is SubCounty) {
                    viewModel.selectedSubCountyDto.postValue(data)
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }
        })
        task.execute(viewModel.selectedFarmer.value?.subDistrict)
    }


    /**
     * fetching all villages storing in DataHolder and passing village name of farmer to get
     * farmer's village Object
     */
    private fun fetchAllVillages() {
        val task = GetAllVillagesTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Village) {
                    viewModel.selectedVillageDto.postValue(data)
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }
        })
        task.execute(viewModel.selectedFarmer.value?.village)
    }


    private fun fetchRegionList() {
        val task = GetRegionsTask(null)
        task.context = requireContext()
        task.setShowLoading(false)
        task.execute()
    }

    private fun fetchGender() {
        if (DataHolder.getInstance().genderItemList != null
            && DataHolder.getInstance().genderItemList.isNotEmpty()
        ) return
        val task = GetGenderTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    DataHolder.getInstance().genderItemList =
                        listOf(*data as Array<GenderItem?>)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun fetchLandUOMs() {
        val task = GetLandUnitsMeasurements()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val list = data as ArrayList<UOMType>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.landUOMs.clear()
                        viewModel.landUOMs.addAll(list)
                        viewModel.selectedUOM.postValue(list[0].name)
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun fetchEducationLevels() {
        val task = GetEducationLevelsTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<GlobalIndicatorDTO>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.educationLevels.clear()
                        viewModel.educationLevels.addAll(list)
                        viewModel.educationNames.value = list.map { it.name }
                        viewModel.education.postValue(list[0].name)
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun setUserDetails() {
        val farmer = viewModel.getFarmer(mContext)
        if (viewModel.profileBitmap.value != null) {
            try {
                uploadUserImage(farmer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            saveUserProfileService(farmer)
        }
    }


    @Throws(IOException::class)
    private fun uploadUserImage(farmer: Farmer) {
        val profilePicFile = FileUtil()
            .getFileFromBitmap(
                viewModel.profileBitmap.value,
                context,
                "profile_pic"
            )
        val task = UploadProfilePicTask(profilePicFile)
        task.context = requireContext()
        task.setLoadingMessage("Saving profile picture...")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as String).also {
                    farmer.profileImage = it
                    saveUserProfileService(farmer)
                    viewModel.profileBitmap.postValue(null)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun saveUserProfileService(profile: Farmer) {
        val task = SaveFarmerTask(profile)
        task.context = requireContext()
        task.setLoadingMessage(requireContext().getString(R.string.saving_profile))
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                getCurrentUser()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason : $reason\nmessage: $errorMessage")
            }
        })
        task.execute()
    }

    private fun getCurrentUser() {
        val task = GetCurrentFarmerTask()
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Farmer).also {
                    DataHolder.getInstance().selectedFarmer = it
                    viewModel.selectedFarmer.postValue(it)
                    viewModel.lands.clear()
                    viewModel.mapLandDetails()
                }
                onDone()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })

        task.execute()
    }


    private val openDatePickerDialog: () -> Unit = {
        val newFragment: FarmSantaDatePickerDialog = FarmSantaDatePickerDialog()
            .setOnDateSelectListener { year: Int, month: Int, date: Int ->
                ageOfPerson = DateUtil().getAge(year, month, date)
                if (ageOfPerson > 17) {
                    val mm = if (month < 10) "0$month" else month.toString()
                    val dd = if (date < 10) "0$date" else date.toString()
                    val dateString = "$dd-$mm-$year"
                    viewModel.dateOfBirth.postValue(dateString)

//                    editProfileDob.setText(dateString)
//                    editInputDobWrapper.error = null
                } else {
//                    editProfileDob.setText("")
//                    editInputDobWrapper.error = "Age less than 18!"
                }
            }
        newFragment.show(parentFragmentManager, "DatePicker")
        Handler(Looper.getMainLooper()).postDelayed({
            val dpd = newFragment.dateDialog
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Log.d(AppConstants.TAG, "Date picker select")
                dpd?.datePicker?.touchables?.get(1)?.performClick()
            } else {
                Log.d(AppConstants.TAG, "Date picker select post lollipop")
                if (dpd != null) {
                    Log.d(AppConstants.TAG, "Date picker not null")
                    dpd.datePicker.touchables[0].performClick()
                }
            }
        }, 500)
    }

    private fun goToAddFarmLocation(isEdit: Boolean) {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_ADD_FARM_LOCATION_FROM_EDIT_PROFILE,
                isEdit
            )
        }
    }

    private fun goToAddCrops() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_ADD_CROP_FROM_PROFILE,
                true
            )
        }
    }

    private fun onDone() {
        findNavController().popBackStack()
    }
}