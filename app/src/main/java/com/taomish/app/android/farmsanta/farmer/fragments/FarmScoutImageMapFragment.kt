package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting

class FarmScoutImageMapFragment(val scouting: FarmScouting?) : FarmSantaBaseFragment() {
    private lateinit var mapViewImageLocation: MapView
    private lateinit var noLocContainerLinearLayout: LinearLayout

    private var mBundle: Bundle? = null

    private var imageLocation: GoogleMap? = null
    private var markerOptions: MarkerOptions? = null
    private var mLastKnownLocation: Location? = null

    private var mLocationPermissionGranted: Boolean = false

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        if (savedState != null) {
            mBundle = savedState
        }
        return inflater!!.inflate(R.layout.fragment_farm_scout_location, container, false)
    }

    override fun initViewsInLayout() {
        mapViewImageLocation = initThisView(R.id.farmScoutLocation_mapView_location)
        noLocContainerLinearLayout = initThisView(R.id.farmScoutLocation_linear_noLocContainer)

        mapViewImageLocation.onCreate(mBundle)
    }

    override fun initListeners() {
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        mapViewImageLocation.getMapAsync { googleMap ->
            run {
                onMapReady(googleMap)
            }
        }

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(requireContext())
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    override fun initData() {

    }

    override fun onResume() {
        mapViewImageLocation.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapViewImageLocation.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewImageLocation.onLowMemory()
    }

    private fun onMapReady(googleMap: GoogleMap) {
        imageLocation = googleMap
        imageLocation?.mapType = GoogleMap.MAP_TYPE_NORMAL

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(AppConstants.TAG, "Location permission granted")

            mLocationPermissionGranted = true
            getDeviceLocation()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                getDeviceLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */try {
            if (mLocationPermissionGranted) {
                @SuppressLint("MissingPermission") val locationResult: Task<Location> =
                    mFusedLocationProviderClient!!.lastLocation
                locationResult.addOnCompleteListener(
                    requireActivity()
                ) { task: Task<Location> ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.result
                        if (mLastKnownLocation != null) {
                            Log.d(
                                AppConstants.TAG,
                                "Latitude: " + mLastKnownLocation!!.latitude
                            )
                            Log.d(
                                AppConstants.TAG,
                                "Longitude: " + mLastKnownLocation!!.longitude
                            )
                            val latLng = LatLng(
                                mLastKnownLocation!!.latitude,
                                mLastKnownLocation!!.longitude
                            )
                            updateLocation()
                        } else {
                            Log.d(
                                AppConstants.TAG,
                                "Current location is null even in successful task. Using defaults."
                            )
                            updateLocation()
                        }

                    } else {
                        Log.d(
                            AppConstants.TAG,
                            "Current location is null. Using defaults."
                        )
                        updateLocation()
                        Log.e(AppConstants.TAG, "Exception: %s", task.exception)
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message!!)
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        this.scouting?.currentWeather?.let {
            if (it.latitude != 360.0f && it.longitude != 360f) {
                mapViewImageLocation.visibility = View.VISIBLE
                noLocContainerLinearLayout.visibility = View.INVISIBLE

                val loc = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                imageLocation?.uiSettings?.isMyLocationButtonEnabled = false
                imageLocation?.isMyLocationEnabled = true

                markerOptions = MarkerOptions().position(loc).title("Marker in Africa")

                imageLocation?.addMarker(markerOptions!!)
                imageLocation?.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 20F))

                imageLocation?.uiSettings?.isZoomControlsEnabled = true
            } else {
                mapViewImageLocation.visibility = View.GONE
                noLocContainerLinearLayout.visibility = View.VISIBLE
            }
        }
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                getDeviceLocation()
            }
        }

}