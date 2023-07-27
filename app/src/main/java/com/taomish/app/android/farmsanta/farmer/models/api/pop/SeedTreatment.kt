package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.WaterRequirement

data class SeedTreatment(

    @SerializedName("applicationMethod") var applicationMethod: String? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("dosage") var dosage: Dosage? = null,
    @SerializedName("formulation") var formulation: String? = null,
    @SerializedName("modeOfAction") var modeOfAction: String? = null,
    @SerializedName("photos") var photos: ArrayList<Photo>? = null,
    @SerializedName("productLabel") var productLabel: String? = null,
    @SerializedName("productName") var productName: ArrayList<String>? = null,
    @SerializedName("productType") var productType: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("toxicityLevel") var toxicityLevel: String? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("waitingPeriod") var waitingPeriod: String? = null,
    @SerializedName("waterRequirement") var waterRequirement: WaterRequirement? = null
)
