package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_farmer_location.SelectFarmerLocationFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationListener
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationLiveData
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationRequestResult
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import java.io.IOException
import java.util.*


class SelectFarmerLocationFragment : FarmSantaBaseFragment(), LocationListener {

    private val viewModel: SignUpAndEditProfileViewModel by activityViewModels()
    private lateinit var mContext: Context
    private lateinit var requestLocationPermissions: ActivityResultLauncher<Array<String>>
    private lateinit var locationData: LocationLiveData

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
                    SelectFarmerLocationFragmentScreen(
                        viewModel = viewModel,
                        onAddLocationClicked = this@SelectFarmerLocationFragment::goToAddFarmerDetailsFragment
                    )
                }
            }
        }
        return root
    }

    override fun onPause() {
        stopLocationUpdates()
        super.onPause()
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
        mContext = requireContext()
        locationData = LocationLiveData(mContext)
        initLocationListener(
            locationData,
            this
        )
        val args = SelectFarmerLocationFragmentArgs.fromBundle(
            arguments ?: Bundle()
        )
        viewModel.mobileNumber.postValue(args.mobileNumber)
        viewModel.fetchTerritories(mContext)
    }

    private fun goToAddFarmerDetailsFragment() {
        if (viewModel.validateLocation()) {
            if (fragmentChangeHelper != null) {
                fragmentChangeHelper.onFragmentData(
                    AppConstants.FragmentConstants.FRAGMENT_ADD_FARMER,
                    viewModel.mobileNumber.value
                )
            }
        } else {
            mContext.showToast(getString(R.string.validation_msg))
        }
    }

    private fun initLocationListener(
        locationData: LocationLiveData,
        locationListener: LocationListener
    ) {
        locationData.observe(viewLifecycleOwner) {
            when (it) {
                is LocationRequestResult.LocationData -> {
                    Log.v("Location Result", "-----> $it")
//                    hideSnackBar()
                    locationListener.onLocationAvailable(it)
                }
                is LocationRequestResult.LocationAccessFailure -> {
                    Log.v("Location Failure", "-----> $it")
//                    hideSnackBar()
                    locationListener.onLocationFailure()
//                    isGpsOrPermissionRejectedByUser = true
                }
                is LocationRequestResult.LocationResolvableException -> {
                    Log.v("Location Gps Required", "-----> $it")
//                    hideSnackBar()
//                    requestForGps.launch(
//                        IntentSenderRequest.Builder(it.exception.resolution).build()
//                    )
                }
                is LocationRequestResult.LocationPermission -> {
                    Log.v("Permission Required", "-----> $it")
//                    hideSnackBar()
//                    requestForLocationPermission()
                }
                is LocationRequestResult.LocationInProgress -> {
                    Log.v("Location Is In Progress", "-----> $it")
//                    showSnackBar(
//                        "Searching for location please wait",
//                        Snackbar.LENGTH_INDEFINITE
//                    )
                }
                else -> {}
            }
        }
    }

    override fun onLocationAvailable(locationData: LocationRequestResult.LocationData) {

        super.onLocationAvailable(locationData)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onLocationPermissionDeny() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
//            alertDialog(
//                title = getString(R.string.permission_required),
//                message = getString(R.string.perm_img_location_deny_message),
//                btnOneText = getString(R.string.ok),
//                cancelable = false,
//                btnTwoText = "",
//                function = {
//                    requestForLocationPermission()
//                }
//            )
        } else {
            locationData.permissionDenied = true
//            alertDialog(
//                title = getString(R.string.permission_required),
//                message = getString(R.string.msg_location_settings),
//                btnOneText = getString(R.string.ok),
//                btnTwoText = getString(R.string.cancel),
//                cancelable = false,
//                function = {
//                    if (it == Activity.RESULT_OK) {
//                        val intent = Intent()
//                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                        intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
//                        startActivity(intent)
//                        onLocationSettings()
//                    } else {
//                        onLocationFailure()
//                    }
//                }
//            )
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

    private fun stopLocationUpdates() {
        locationData.removeLocationUpdates()
    }

    private fun startLocationUpdates() {
        locationData.startLocationUpdates()
    }

    private fun getLocationNameFromLatLng(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(mContext, Locale.getDefault())
        try {
            val fromLocation = geocoder.getFromLocation(latitude, longitude, 1)
            if (fromLocation != null && fromLocation.size > 0) {
                return fromLocation[0].locality + ", " + fromLocation[0].subAdminArea
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }
}