package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.ProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.ProfileFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.StartActivityConstants.CAMERA_REQUEST
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.StartActivityConstants.GALLERY_REQUEST
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region
import com.taomish.app.android.farmsanta.farmer.utils.FileUtil
import com.taomish.app.android.farmsanta.farmer.utils.asBitmap
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import java.io.IOException


@Suppress("UNCHECKED_CAST")
class ProfileFragment : FarmSantaBaseFragment() {

    private lateinit var mContext: Context

    private val viewModel: ProfileViewModel by activityViewModels()

    private var permissionRequest = -1

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                mContext.showToast(R.string.permission_granted_msg)
                when (permissionRequest) {
                    GALLERY_REQUEST -> openGallery()
                    CAMERA_REQUEST -> openCamera()
                    else -> Unit
                }
            } else {
                mContext.showToast(R.string.permission_denied)
            }
        }


    private val selectImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.profileBitmap = uri.asBitmap(requireContext())
                viewModel.isImageSelected = true
            }
        }

    private val captureFromCamera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
            if (result != null) {
                viewModel.profileBitmap = result
                viewModel.isImageSelected = true
            } else {
                mContext.showToast("failed to capture image")
            }
        }

    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?,
    ): View? {
        return null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onResume() {
        super.onResume()
        DataHolder.getInstance().selectedFarmer?.let(viewModel.farmer::postValue)
    }

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
                    ProfileFragmentScreen(
                        viewModel = viewModel,
                        onEditClicked = this@ProfileFragment::goToProfileEditFragment,
                        onBackPressed = this@ProfileFragment::onBackPressed,
                        onViewFarm = this@ProfileFragment::goToPlotMapActivity,
                        onSelectCamera = this@ProfileFragment::takePicture,
                        onSelectGallery = this@ProfileFragment::takeGalleryPic,
                        onDone = this@ProfileFragment::setUserDetails
                    )
                }
            }
        }
        return root
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        if (DataHolder.getInstance().selectedFarmer !== null) {
            viewModel.farmer.postValue(DataHolder.getInstance().selectedFarmer)
            viewModel.fetchMyCrops(mContext)
            fetchRegionsByTerritory(mContext)
            return
        }
        getCurrentUser()
    }


    private fun goToPlotMapActivity(selectedLand: Int) {
        fragmentChangeHelper.onFragmentData(
            AppConstants.FragmentConstants.FARMER_MAP_FRAGMENT,
            selectedLand
        )
    }


    private fun goToProfileEditFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_PROFILE_EDIT)
        }
    }

    private fun getCurrentUser() {
        val task = GetCurrentFarmerTask()
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Farmer).also {
                    DataHolder.getInstance().selectedFarmer = it
                    viewModel.farmer.postValue(it)
                    viewModel.fetchMyCrops(mContext)
                    fetchRegionsByTerritory(mContext)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })

        task.execute()
    }


    private fun onBackPressed() {
        (activity as MainActivity).navController.popBackStack()
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
        selectImage.launch("image/*")
    }

    private fun setUserDetails() {
        viewModel.farmer.value?.let {
            if (viewModel.profileBitmap != null) {
                try {
                    uploadUserImage(it)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                saveUserProfileService(it)
            }
        }
    }


    @Throws(IOException::class)
    private fun uploadUserImage(farmer: Farmer) {
        val profilePicFile = FileUtil()
            .getFileFromBitmap(
                viewModel.profileBitmap,
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
                    viewModel.profileBitmap = null
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("upload image failed ")
            }
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
                viewModel.isImageSelected = false
                getCurrentUser()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason : $reason\nmessage: $errorMessage")
                viewModel.isImageSelected = false
                viewModel.profileBitmap = null
            }
        })
        task.execute()
    }


    private fun updateFarmer(profile: Farmer) {
        val task = UpdateFarmerTask(profile)
        task.context = requireContext()
        task.setLoadingMessage(requireContext().getString(R.string.saving_profile))
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Farmer) viewModel.farmer.postValue(data)
                viewModel.isImageSelected = false
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason : $reason\nmessage: $errorMessage")
                viewModel.isImageSelected = false
                viewModel.profileBitmap = null
            }
        })
        task.execute()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.isImageSelected = false
        viewModel.profileBitmap = null
    }

    fun fetchRegionsByTerritory(context: Context) {
        val task = GetRegionsByTerritoryTask()
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage(context.getString(R.string.regions_loading_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Region>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.region = list.find {
                            it.uuid == viewModel.farmer.value?.region?.firstOrNull()
                        }?.regionName ?: ""
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                context.showToast("${context.getString(R.string.message)}: $errorMessage")
            }
        })
        task.execute(viewModel.farmer.value?.territory?.firstOrNull())
    }
}