package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.PreHarvestInterval
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.WaterRequirement

data class Fungicide(

    @SerializedName("applicationMethod") var applicationMethod: String? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("crops") var crops: ArrayList<String>? = arrayListOf(),
    @SerializedName("cultivarGroups") var cultivarGroups: ArrayList<String>? = arrayListOf(),
    @SerializedName("cultivars") var cultivars: ArrayList<String>? = arrayListOf(),
    @SerializedName("diseases") var diseases: ArrayList<String>? = arrayListOf(),
    @SerializedName("dosage") var dosage: Dosage? = Dosage(),
    @SerializedName("dosageMethod") var dosageMethod: String? = null,
    @SerializedName("formulation") var formulation: String? = null,
    @SerializedName("formulationType") var formulationType: String? = null,
    @SerializedName("modeOfAction") var modeOfAction: String? = null,
    @SerializedName("molecule") var molecule: ArrayList<Molecule>? = arrayListOf(),
    @SerializedName("photos") var photos: ArrayList<Photo>? = arrayListOf(),
    @SerializedName("preHarvestInterval") var preHarvestInterval: PreHarvestInterval? = PreHarvestInterval(),
    @SerializedName("productName") var productName: ArrayList<String>? = arrayListOf(),
    @SerializedName("productType") var productType: String? = null,
    @SerializedName("regions") var regions: ArrayList<String>? = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("territories") var territories: ArrayList<String>? = arrayListOf(),
    @SerializedName("toxicityLevel") var toxicityLevel: String? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("waterRequirement") var waterRequirement: WaterRequirement? = WaterRequirement()
)
