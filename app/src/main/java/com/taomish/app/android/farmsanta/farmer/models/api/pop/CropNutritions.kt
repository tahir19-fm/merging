package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class CropNutrition(
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("crop") var crop: String? = null,
    @SerializedName("cultivarGroups") var cultivarGroups: ArrayList<String>? = arrayListOf(),
    @SerializedName("cultivars") var cultivars: ArrayList<String>? = arrayListOf(),
    @SerializedName("dosages") var dosages: ArrayList<NutritionDosage>? = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null
)
