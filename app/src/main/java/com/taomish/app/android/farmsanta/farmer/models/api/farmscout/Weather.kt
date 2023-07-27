package com.taomish.app.android.farmsanta.farmer.models.api.farmscout

import com.google.gson.annotations.SerializedName

data class Weather (
    @SerializedName("dt") val dt : Long,
    @SerializedName("temp") val temp : Double,
    @SerializedName("longitude") val longitude : Float,
    @SerializedName("latitude") val latitude : Float,
    @SerializedName("pressure") val pressure : Double,
    @SerializedName("humidity") val humidity : Double,
    @SerializedName("wind") val wind : Double,
    @SerializedName("uvi") val uvi : Double,
    @SerializedName("clouds") val clouds : Double,
    @SerializedName("visibility") val visibility : Double,
    @SerializedName("rain") val rain : String
)