package com.taomish.app.android.farmsanta.farmer.models.view_model

import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException

sealed class LocationRequestResult {

    data class LocationData(
        val location: Location
    ): LocationRequestResult()

    data class LocationAccessFailure(
        val message: String? = null
    ): LocationRequestResult()

    data class LocationResolvableException(
        val exception: ResolvableApiException
    ): LocationRequestResult()

    data class LocationPermission(
        val isPermissionRequired: Boolean
    ): LocationRequestResult()

    data class LocationInProgress(
        val data: Any? = null
    ): LocationRequestResult()
}