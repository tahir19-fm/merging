package com.taomish.app.android.farmsanta.farmer.models.api.farmscout

import com.google.gson.annotations.SerializedName
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder

data class FarmScouting(
    @SerializedName("id") val id : Int? = -1,
    @SerializedName("uuid") val uuid : String? = null,
    @SerializedName("createdBy") val createdBy : String? = null,
    @SerializedName("updatedBy") val updatedBy : String? = null,
    @SerializedName("createdTimestamp") val createdTimestamp : String? = null,
    @SerializedName("updatedTimestamp") val updatedTimestamp : String? = null,
    @SerializedName("tenantId") val tenantId : String? = null,
    @SerializedName("landId") val landId : String,
    @SerializedName("crop") val crop : String,
    @SerializedName("cropStage") val cropStage : String? = null,
    @SerializedName("images") val images : List<ScoutImage> = emptyList(),
    @SerializedName("caption") val caption : String,
    @SerializedName("currentWeather") val currentWeather : Weather,
    @SerializedName("farmerId") val farmerId : String,
    @SerializedName("advisoryExist") var advisoryExist : Boolean = false,
    @SerializedName("region") val region: List<String>? = null,
    @SerializedName("territory") val territory: List<String>? = null

) {
    fun getCropName(): String {
        return DataHolder.getInstance().cropMasterMap[crop]?.cropName ?: ""
    }
}