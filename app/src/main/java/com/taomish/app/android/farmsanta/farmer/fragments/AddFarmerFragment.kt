package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetEducationLevelsTask
import com.taomish.app.android.farmsanta.farmer.background.GetGenderTask
import com.taomish.app.android.farmsanta.farmer.background.GetLandUnitsMeasurements
import com.taomish.app.android.farmsanta.farmer.background.SubscribeToPushTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SelectLanguageViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer.AddFarmerFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.StartActivityConstants.CAMERA_REQUEST
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.StartActivityConstants.GALLERY_REQUEST
import com.taomish.app.android.farmsanta.farmer.controller.NavigationController
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.fragments.dialog.FarmSantaDatePickerDialog
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.gender.GenderItem
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.master.UOMType
import com.taomish.app.android.farmsanta.farmer.models.api.notification.Subscribe
import com.taomish.app.android.farmsanta.farmer.models.api.user.UserToken
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationRequestResult
import com.taomish.app.android.farmsanta.farmer.utils.*
import java.io.IOException

@Suppress("OVERRIDE_DEPRECATION", "UNCHECKED_CAST")
class AddFarmerFragment : FarmSantaBaseFragment() {
    private var userProfileBitmap: Bitmap? = null
    private var ageOfPerson = 0
    private lateinit var mContext: Context
    private var permissionRequest = -1
    private lateinit var prefs: AppPrefs

    private val viewModel: SignUpAndEditProfileViewModel by activityViewModels()
    private val languageViewModel: SelectLanguageViewModel by activityViewModels()

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                mContext.showToast(R.string.permission_granted_msg)
                if (permissionRequest == GALLERY_REQUEST) {
                    openGallery()
                } else {
                    openCamera()
                }
            } else {
                mContext.showToast(R.string.permission_denied)
            }
        }


    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.profileBitmap.postValue(uri.asBitmap(requireContext()))
                val path = FileUtil().copyFile(requireContext(), uri)
                userProfileBitmap = BitmapFactory.decodeFile(path)
                try {
                    userProfileBitmap = ImageUtil().rotateIfImageNeeded(userProfileBitmap, path)
                } catch (_: IOException) {
                }
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

    override fun init() {
        ageOfPerson = 0
    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View? {
        return null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initData()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                    AddFarmerFragmentScreen(
                        viewModel = viewModel,
                        openDatePickerDialog = openDatePickerDialog,
                        goToAddCrops = this@AddFarmerFragment::goToAddCrops,
                        goToAddOrEditFarmLocation = this@AddFarmerFragment::goToAddOrEditFarmLocation,
                        onSelectCamera = this@AddFarmerFragment::takePicture,
                        onSelectGallery = this@AddFarmerFragment::takeGalleryPic,
                        onDone = {
                            if (viewModel.validateFarmer(requireContext())) {
                                viewModel.setUserDetails(
                                    context = mContext
                                ) {
                                    sendFCMTokenToServer()
                                    goToHome(it)
                                }
                            }
                        }
                    )
                }
            }
        }
        return root
    }

    private fun goToAddCrops() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_ADD_CROP,
                false
            )
        }
    }

    private fun goToAddOrEditFarmLocation(isEdit: Boolean) {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_ADD_FARM_LOCATION,
                isEdit
            )
        }
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        prefs = AppPrefs(mContext)
        val args = AddFarmerFragmentArgs.fromBundle(
            arguments ?: Bundle()
        )
        viewModel.mobileNumber.postValue(args.mobileNumber)
        viewModel.mobileNumber.postValue(prefs.phoneNumber)
        viewModel.countryCode.postValue(prefs.countryCode)
        viewModel.fetchTerritories(mContext)
        viewModel.selectedTerritoryDto.postValue(languageViewModel.selectedTerritoryDto.value)
        viewModel.selectedTerritories.add(languageViewModel.selectedTerritoryDto.value?.uuid ?: "")
        viewModel.territory.postValue(languageViewModel.selectedTerritoryDto.value?.territoryName ?: "")
        viewModel.fetchGenders(mContext)
        viewModel.fetchRegionsByTerritory(mContext)
        fetchGender()
        fetchLandUOMs()
        fetchEducationLevels()
    }


    override fun onStart() {
        super.onStart()
        locationPermission()
    }


    private fun locationPermission() {
        if (requireActivity() is FarmSantaBaseActivity) {
            if (DataHolder.getInstance().weatherAll == null) {
                (requireActivity() as FarmSantaBaseActivity).requestForLocation()
            }
            (requireActivity() as FarmSantaBaseActivity)
                .locationDataMutableLiveData.observe(viewLifecycleOwner) { (location): LocationRequestResult.LocationData ->
                    DataHolder.getInstance().lastKnownLocation = location
                }
        }
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
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
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


    private fun goToHome(token: UserToken) {
        prefs.userToken = token.token
        prefs.refreshToken = token.refreshToken
        prefs.isUserProfileCompleted = true
        NavigationController.getInstance(requireActivity())
            .onFragmentChange(AppConstants.FragmentConstants.FARMER_HOME_FRAGMENT)
    }



    private fun fetchGender() {
        if (DataHolder.getInstance().genderItemList != null
            && DataHolder.getInstance().genderItemList.isNotEmpty()
        ) { return }
        val task = GetGenderTask()
        task.context = mContext
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
        viewModel.landUOMs.clear()
        val task = GetLandUnitsMeasurements()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val list = data as ArrayList<UOMType>?
                    if (!list.isNullOrEmpty()) {
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
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    if (data.isNotEmpty()) {
                        val list = data.toList() as List<GlobalIndicatorDTO>
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


    private fun sendFCMTokenToServer() {
        val subscribe = Subscribe()
        subscribe.tokens = listOf(prefs.firebaseToken ?: "")
        val task = SubscribeToPushTask(subscribe)
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Boolean) {
                    prefs.isTokenSent = data
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                prefs.isTokenSent = false
            }
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
                    viewModel.dateOfBirth.postValue(DateUtil().getDateMonthYearFormat(dateString))
                } else {
                    showToast(R.string.age_not_valid_msg)
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


}