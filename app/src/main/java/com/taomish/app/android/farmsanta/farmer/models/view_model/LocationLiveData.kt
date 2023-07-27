package com.taomish.app.android.farmsanta.farmer.models.view_model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.taomish.app.android.farmsanta.farmer.R

class LocationLiveData(val context: Context) : LiveData<LocationRequestResult?>() {

    var permissionDenied: Boolean = false
    private var settingsClient: SettingsClient? = null
    private var locationSettingRequest: LocationSettingsRequest? = null
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            postUpdates(
                LocationRequestResult.LocationData(
                    locationResult.lastLocation!!
                )
            )
        }
    }

    init {
        settingsClient = LocationServices.getSettingsClient(context)
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(LocationRequest.create())
        locationSettingRequest = builder.build()
    }

    override fun onActive() {
        super.onActive()
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (permissionDenied) {
                permissionDenied = false
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.also {
                            postUpdates(
                                LocationRequestResult.LocationData(
                                    location
                                )
                            )
                        }
                    }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        removeLocationUpdates()
    }

    fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permissionDenied = false
            locationSettingRequest?.let { locationSettingReq ->
                settingsClient?.checkLocationSettings(locationSettingReq)
                    ?.addOnSuccessListener {
                        postUpdates(LocationRequestResult.LocationInProgress())
                        fusedLocationClient.requestLocationUpdates(
                            LocationRequest.create().apply {
                                interval = 2000
                                fastestInterval = 1000
                                priority = PRIORITY_HIGH_ACCURACY
                                numUpdates = 3
                            },
                            locationCallback,
                            Looper.getMainLooper()
                        )
                    }?.addOnFailureListener {
                        if (it is ResolvableApiException) {
                            postUpdates(
                                LocationRequestResult.LocationResolvableException(it)
                            )
                        } else {
                            postUpdates(
                                LocationRequestResult.LocationAccessFailure(
                                    context.getString(R.string.location_access_failed)
                                )
                            )
                        }
                    }
            }
        } else {
            if (permissionDenied) {
                postUpdates(
                    LocationRequestResult.LocationAccessFailure(context.getString(R.string.location_access_failed))
                )
            } else {
                postUpdates(
                    LocationRequestResult.LocationPermission(true)
                )
            }
        }
    }

    fun onGpsTurnOn(isTurnedOn: Boolean) {
        if (isTurnedOn) {
            startLocationUpdates()
        } else {
            postUpdates(
                LocationRequestResult.LocationAccessFailure(context.getString(R.string.gps_off))
            )
        }
    }

    fun onPermissionGranted() {
        startLocationUpdates()
    }

    fun removeLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun postUpdates(update: LocationRequestResult) {
        if (value != update || update is LocationRequestResult.LocationData) {
            postValue(update)
        }
    }

    fun clearFields() {
        permissionDenied = false
        postValue(null)
    }
}