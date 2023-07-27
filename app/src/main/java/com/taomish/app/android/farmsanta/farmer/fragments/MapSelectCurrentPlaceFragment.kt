package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants

class MapSelectCurrentPlaceFragment : FarmSantaBaseFragment() {
    private var buttonSelectCurrentPlace: Button? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var locationCallback: LocationCallback
    private var mLastKnownLocation: Location? = null
    private var mLocationPermissionGranted = false
    private var isFetchingCurrentLocation = false
    private var isEdit = false
    override fun init() {
        isFetchingCurrentLocation = false
    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        return inflater.inflate(R.layout.fragment_map_select_current, container, false)
    }

    override fun initViewsInLayout() {
        buttonSelectCurrentPlace = initThisView(R.id.mapSelectCurrent_button_pickCurrentPlace)
    }

    override fun initListeners() {
        buttonSelectCurrentPlace!!.setOnClickListener { v: View? -> locationPermission }
    }

    override fun initData() {
        mFusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(
                requireActivity()
            )
    }

    private val locationPermission: Unit
        private get() {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionGranted = true
                Log.d(AppConstants.TAG, "Permission granted")
                deviceLocation
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        }

    // Set the map's camera position to the current location of the device.
    private val deviceLocation: Unit
        private get() {
            try {
                if (mLocationPermissionGranted) {
                    @SuppressLint("MissingPermission") val locationResult =
                        mFusedLocationProviderClient!!.lastLocation
                    locationResult.addOnCompleteListener(requireActivity()) { task: Task<Location?> ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.result
                            if (mLastKnownLocation != null) {
                                Log.d(
                                    AppConstants.TAG,
                                    "Last known Latitude: " + mLastKnownLocation!!.latitude
                                )
                                Log.d(
                                    AppConstants.TAG,
                                    "Last known Longitude: " + mLastKnownLocation!!.longitude
                                )
                            } else {
                                Log.d(
                                    AppConstants.TAG,
                                    "Current location is null even in successful task. Using defaults.$task"
                                )
                                mLastKnownLocation = Location("")
                                mLastKnownLocation!!.latitude = -01.08
                                mLastKnownLocation!!.longitude = 34.12
                                updateLocation()
                            }
                        } else {
                            Log.d(AppConstants.TAG, "Current location is null. Using defaults.")
                            Log.e(AppConstants.TAG, "Exception: %s", task.exception)
                            mLastKnownLocation = Location("")
                            mLastKnownLocation!!.latitude = -01.08
                            mLastKnownLocation!!.longitude = 34.12
                            updateLocation()
                        }
                    }
                }
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message!!)
            }
            if (!isFetchingCurrentLocation) {
                isFetchingCurrentLocation = true
                requestLocationUpdates()
            }
        }

    private fun requestLocationUpdates() {
        Log.d(AppConstants.TAG, "Requesting updates")
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (20 * 1000).toLong()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                Log.d(AppConstants.TAG, "Got result of update")
                Log.d(AppConstants.TAG, "location results are --> $locationResult")
                for (location in locationResult.locations) {
                    if (location != null) {
                        mLastKnownLocation = location
                        if (mFusedLocationProviderClient != null) {
                            mFusedLocationProviderClient!!.removeLocationUpdates(locationCallback!!)
                        }
                        Log.d(AppConstants.TAG, "Got location ping update ==> $mLastKnownLocation")
                        updateLocation()
                    }
                }
            }
        }
        if (ActivityCompat
                .checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat
                .checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(AppConstants.TAG, "Necessary permissions not granted")
            return
        }
        mFusedLocationProviderClient
            ?.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun updateLocation() {
        isFetchingCurrentLocation = false
        val fragmentActivity: FragmentActivity
        fragmentActivity = try {
            requireActivity()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            farmSantaContext as FragmentActivity
        }
        (fragmentActivity as FarmerMapSelectActivity)
            .updateMap(
                AppConstants.DataTransferConstants.KEY_MAP_CURRENT,
                mLastKnownLocation, isEdit
            )
    }

    private fun tellActivityItIsEditing() {
        val fragmentActivity: FragmentActivity
        fragmentActivity = try {
            requireActivity()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            farmSantaContext as FragmentActivity
        }
        (fragmentActivity as FarmerMapSelectActivity)
            .updateMap(
                AppConstants.DataTransferConstants.KEY_MAP_EDIT_LOC
            )
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001
    }
}