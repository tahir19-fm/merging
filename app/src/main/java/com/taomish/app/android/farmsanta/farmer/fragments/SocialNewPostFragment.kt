package com.taomish.app.android.farmsanta.farmer.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.BuildConfig
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetRegionsTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialNewPostViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_new_post.SocialNewPostFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region
import com.taomish.app.android.farmsanta.farmer.utils.saveFileInStorage
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import java.io.File

class SocialNewPostFragment : FarmSantaBaseFragment() {

    private val socialNewPostViewModel: SocialNewPostViewModel by viewModels()
    private lateinit var photoFile: File
    private lateinit var takePicResultLauncher: ActivityResultLauncher<Intent>


    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                openCamera()
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                showCameraPermissionAlert()
            } else {
                context?.showToast("Permission denied please allow camera access from app settings")
            }
        }

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                saveFileInStorage(
                    context = this.requireContext(),
                    uri = uri
                )?.let { socialNewPostViewModel.images.add(it) }
            }
        }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                context?.showToast(R.string.permission_granted_msg)
                openGallery()
            } else {
                context?.showToast(R.string.permission_denied)
            }
        }

    private fun showCameraPermissionAlert() {
        (requireActivity() as FarmSantaBaseActivity).alertDialog(
            message = "Camera permission is required to capture the image",
            cancelable = false,
            function = {
                if (it == Activity.RESULT_OK) {
                    openCamera()
                }
            }
        )
    }

    private fun showStoragePermissionAlert() {
        (requireActivity() as FarmSantaBaseActivity).alertDialog(
            message = "Storage permission is required to pick the image from gallery",
            cancelable = false,
            function = {
                if (it == Activity.RESULT_OK) {
                    takeGalleryPic()
                }
            }
        )
    }

    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle,
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(
            activity, R.style.AppThemeMaterial
        )
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_social_new, container, false)
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
                    SocialNewPostFragmentScreen(
                        viewModel = socialNewPostViewModel,
                        onSelectCamera = { onSelectCamera() },
                        onSelectGallery = { onSelectGallery() },
                        onAddPost = { saveMessage() }
                    )
                }
            }
        }
        return root
    }

    private fun saveMessage() {
        if (isValidPostData()) {
            socialNewPostViewModel.postMessage(requireContext()) {
                (requireActivity() as FarmSantaBaseActivity).alertDialog(
                    message = getString(R.string.post_submitted_successfully),
                    cancelable = false,
                    btnOneText = getString(R.string.ok),
                    btnTwoText = "",
                    function = { findNavController().popBackStack() }
                )
            }
        }
    }

    private fun isValidPostData(): Boolean {
        /*if (socialNewPostViewModel.images.isEmpty()) {
            context?.showToast(getString(R.string.please_add_image))
            return false
        }*/
        if (socialNewPostViewModel.postTitleText.value.isEmpty()) {
            context?.showToast(getString(R.string.please_give_post_title))
            return false
        }
        if (socialNewPostViewModel.talkDetailText.value.isEmpty()) {
            context?.showToast(getString(R.string.please_add_description))
            return false
        }
        if (socialNewPostViewModel.tags.isEmpty()) {
            context?.showToast(getString(R.string.please_add_tag))
            return false
        }
        /*if (socialNewPostViewModel.selectedRegions.isEmpty()) {
            context?.showToast(getString(R.string.please_select_region))
            return false
        }*/
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takePicResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                socialNewPostViewModel.images.add(photoFile)
            }
        }
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    @SuppressLint("SetTextI18n")
    override fun initData() {
        /* if (socialNewPostViewModel.regionArrayList.isEmpty()) {
             fetchRegionList()
         }
         loadRegions()*/
    }

    /*Get Cultivar List API*/
    @Suppress("UNCHECKED_CAST")
    private fun fetchRegionList() {
        val task = GetRegionsTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    (data as Array<Region>).also {
                        socialNewPostViewModel.regionArrayList = it.toList()
                    }
                    loadRegions()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    /*Set crops data to current crop adapter*/
    private fun loadRegions() {
        socialNewPostViewModel.regionArrayList.forEach { region ->
            socialNewPostViewModel.allRegionsList.add(region.regionName)
        }
    }

    private fun goToHomePage() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.GO_BACK)
        }
    }

    private fun onSelectCamera() {
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            showCameraPermissionAlert()
        } else {
            openCamera()
        }
    }

    private fun onSelectGallery() {
        // Do Something on Gallery Option is Selected
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showStoragePermissionAlert()
        } else {
            takeGalleryPic()
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                cameraPermissionRequest.launch(android.Manifest.permission.CAMERA)
            } else {
                takePicResultLauncher.launch(cameraIntent)
            }
        } else {
            takePicResultLauncher.launch(cameraIntent)
        }
    }

    private fun getPhotoFile(): File {
        val storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", storageDirectory)
    }

    private fun openGallery() {
        takePhoto.launch("image/*")
    }

    private fun takeGalleryPic() {
        val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissions.all {
                    ActivityCompat.checkSelfPermission(requireActivity(), it) !=
                            PackageManager.PERMISSION_GRANTED
                }) {
                requestGalleryPermission.launch(permissions)
            } else {
                openGallery()
            }
        } else {
            openGallery()
        }
    }
}