package com.taomish.app.android.farmsanta.farmer.models.view_model

interface LocationListener {
    fun onLocationAvailable(locationData: LocationRequestResult.LocationData) {}
    fun onLocationFailure() {}
    fun onLocationPermissionDeny() {}
    fun onLocationSettings() {}

}