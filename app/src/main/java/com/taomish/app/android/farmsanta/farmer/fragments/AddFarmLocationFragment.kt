package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.BuildConfig
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity
import com.taomish.app.android.farmsanta.farmer.background.GetWaterSourceTask
import com.taomish.app.android.farmsanta.farmer.background.UploadLandDocTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location.AddFarmLocationFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Area
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Coordinate
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationListener
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationLiveData
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationRequestResult
import com.taomish.app.android.farmsanta.farmer.utils.getCenterCoordinate
import com.taomish.app.android.farmsanta.farmer.utils.getLocationName
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import java.io.File

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class AddFarmLocationFragment : FarmSantaBaseFragment(), LocationListener {

    private val viewModel: SignUpAndEditProfileViewModel by activityViewModels()
    private lateinit var mContext: Context
    private lateinit var requestLocationPermissions: ActivityResultLauncher<Array<String>>
    private lateinit var locationData: LocationLiveData
    private var farmer: Farmer? = null
    private var isEditLand = false
    private var fileArrayList: ArrayList<File> = ArrayList()


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
        initLocationListener(locationData, this)
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    AddFarmLocationFragmentScreen(
                        viewModel = viewModel,
                        onTrackLocationClicked = this@AddFarmLocationFragment::goToMapView,
                        onAddFarmClicked = this@AddFarmLocationFragment::onAddFarm
                    )
                }
            }
        }
        return root
    }

    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        val args = AddFarmLocationFragmentArgs.fromBundle(
            arguments ?: Bundle()
        )
        isEditLand = args.isEditFarmer
        mContext = requireContext()
        if (viewModel.isEditingFarm) {
            farmer = DataHolder.getInstance().selectedFarmer
            viewModel.loadLand(mContext)
        }
        locationData = LocationLiveData(mContext)
        fetchWaterSources()
    }


    private fun onAddFarm() {
        if (viewModel.validateFarmDetails(mContext)) {
            if (fileArrayList.size > 0) uploadLandDocToServer() else
                saveLandDetails()
        }
    }


    private fun initLocationListener(
        locationData: LocationLiveData,
        locationListener: LocationListener
    ) {
        locationData.observe(viewLifecycleOwner) {
            when (it) {
                is LocationRequestResult.LocationData -> {
                    viewModel.isFetchingLocation.postValue(false)
                    locationListener.onLocationAvailable(it)
                }
                is LocationRequestResult.LocationAccessFailure -> {
                    viewModel.isFetchingLocation.postValue(false)
                }
                is LocationRequestResult.LocationResolvableException -> {
                    viewModel.isFetchingLocation.postValue(false)
                }
                is LocationRequestResult.LocationPermission -> {
                    viewModel.isFetchingLocation.postValue(false)
                }
                is LocationRequestResult.LocationInProgress -> {
                    viewModel.isFetchingLocation.postValue(true)
                }
                else -> {}
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onLocationPermissionDeny() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            alertDialog(
                title = getString(R.string.permission_required),
                message = getString(R.string.perm_img_location_deny_message),
                btnOneText = getString(R.string.ok),
                cancelable = false,
                btnTwoText = "",
                function = {
                    requestForLocationPermission()
                }
            )
        } else {
            locationData.permissionDenied = true
            alertDialog(
                title = getString(R.string.permission_required),
                message = getString(R.string.msg_location_settings),
                btnOneText = getString(R.string.ok),
                btnTwoText = getString(R.string.cancel),
                cancelable = false,
                function = {
                    if (it == Activity.RESULT_OK) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        startActivity(intent)
                        onLocationSettings()
                    } else {
                        onLocationFailure()
                    }
                }
            )
        }
    }

    override fun onLocationSettings() {
        super.onLocationSettings()
        locationData.clearFields()
    }

    private fun requestForLocationPermission() {
        requestLocationPermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

//    private fun stopLocationUpdates() {
//        locationData.removeLocationUpdates()
//    }
//
//    private fun startLocationUpdates() {
//        locationData.startLocationUpdates()
//    }


    private fun fetchWaterSources() {
        viewModel.waterSources.clear()
        if (DataHolder.getInstance().waterSourceList != null) {
            DataHolder.getInstance().waterSourceList.forEach {
                viewModel.waterSources.add(it.name ?: "")
            }
        } else {
            val task = GetWaterSourceTask()
            task.context = mContext
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    if (data is ArrayList<*>) {
                        val list = data as ArrayList<GlobalIndicatorDTO>?
                        list?.forEach {
                            viewModel.waterSources.add(it.name ?: "")
                        }

                    }
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }
    }


    private fun saveLandDetails() {
        val land: Land
        var area: Area?
        val landArea = viewModel.landArea.value
        val waterSource = viewModel.waterSource.value
        if (viewModel.isEditingFarm && viewModel.lands.isNotEmpty() && viewModel.selectedLandIndex > -1) {
            land = viewModel.lands[viewModel.selectedLandIndex]
            area = land.area
            if (area == null) area = Area()
            area.unit = try {
                landArea.toDouble()
            } catch (e: Exception) {
                0.0
            }
            area.uom = viewModel.selectedUOM.value
            land.registrationNumber = viewModel.registrationNo.value
            land.area = area
            land.waterSource = waterSource
            land.documents = viewModel.docs
            land.landName = viewModel.farmName.value
            land.coordinates = viewModel.coordinates.value.mapIndexed { i, latLng ->
                Coordinate().apply {
                    index = i
                    latitude = latLng.latitude
                    longitude = latLng.longitude
                }
            }
            viewModel.coordinates.value.getCenterCoordinate()?.let {
                land.latitude = it.latitude
                land.longitude = it.longitude
                land.farmLocation = it.getLocationName(mContext)
            }
            land.crops = viewModel.landCrops
            viewModel.lands[viewModel.selectedLandIndex] = land
            Log.d(this.javaClass.name, "saveLandDetails: updated land")
        } else {
            land = Land()
            area = Area()
            area.unit = try {
                landArea.toDouble()
            } catch (e: Exception) {
                0.0
            }
            area.uom = viewModel.selectedUOM.value
            land.registrationNumber = viewModel.registrationNo.value
            land.area = area
            land.waterSource = waterSource
            land.documents = viewModel.docs
            land.landName = viewModel.farmName.value
            land.coordinates = viewModel.coordinates.value.mapIndexed { i, latLng ->
                Coordinate().apply {
                    index = i
                    latitude = latLng.latitude
                    longitude = latLng.longitude
                }
            }
            viewModel.coordinates.value.getCenterCoordinate()?.let {
                land.latitude = it.latitude
                land.longitude = it.longitude
                land.farmLocation = it.getLocationName(mContext)
            }
            land.crops = viewModel.landCrops
            viewModel.lands.add(land)
        }
        onBack()
    }


    private fun uploadLandDocToServer() {
        val task = UploadLandDocTask(fileArrayList)
        task.context = requireContext()
        task.setLoadingMessage("Uploading picture")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    viewModel.docs = data as ArrayList<String>
                }
                saveLandDetails()
                // saveUserProfileService(mFarmer);
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        //        task.begin();
        task.execute()
    }

    private fun onBack() {
        viewModel.clearFarmFields()
        (activity as MainActivity).navController.popBackStack()
    }

    private fun alertDialog(
        message: String,
        btnOneText: String = getString(R.string.yes),
        btnTwoText: String = getString(R.string.no),
        function: (Int) -> Unit,
        cancelable: Boolean = true,
        title: String? = getString(R.string.app_name),
//        isSpanned: Boolean = false
    ) {
        try {
            val builder = AlertDialog.Builder(mContext)
            builder.setTitle(title ?: getString(R.string.app_name))
            builder.setMessage(
                message
            )
            builder.setPositiveButton(btnOneText) { dialog, _ ->
                function.invoke(Activity.RESULT_OK)
                dialog?.dismiss()
            }
            if (btnTwoText.isNotEmpty()) {
                builder.setNegativeButton(btnTwoText) { dialog, _ ->
                    function.invoke(Activity.RESULT_CANCELED)
                    dialog?.dismiss()
                }
            }
            val dialog = builder.create()
            dialog.setOnShowListener {
                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(mContext, R.color.grey_text))
            }
            dialog.setCancelable(cancelable)
            dialog.setCanceledOnTouchOutside(cancelable)
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun goToMapView(selectedLandIndex: Int = -1) {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_MAP_VIEW_FROM_ADD_FARM_LOCATION,
                selectedLandIndex
            )
        }
    }
}