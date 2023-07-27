package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.PreHarvestInterval
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.WaterRequirement

data class Insecticide(

    @SerializedName("applicationMethod") var applicationMethod: String? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("crops") var crops: ArrayList<String>? = null,
    @SerializedName("cultivarGroups") var cultivarGroups: ArrayList<String>? = null,
    @SerializedName("cultivars") var cultivars: ArrayList<String>? = null,
    @SerializedName("dosage") var dosage: Dosage? = Dosage(),
    @SerializedName("dosageMethod") var dosageMethod: String? = null,
    @SerializedName("formulation") var formulation: String? = null,
    @SerializedName("formulationType") var formulationType: String? = null,
    @SerializedName("insects") var insects: ArrayList<String>? = null,
    @SerializedName("modeOfAction") var modeOfAction: String? = null,
    @SerializedName("molecule") var molecule: ArrayList<Molecule>? = null,
    @SerializedName("photos") var photos: ArrayList<Photo>? = null,
    @SerializedName("preHarvestInterval") var preHarvestInterval: PreHarvestInterval? = PreHarvestInterval(),
    @SerializedName("productName") var productName: ArrayList<String>? = null,
    @SerializedName("productType") var productType: String? = null,
    @SerializedName("regions") var regions: ArrayList<String>? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("territories") var territories: ArrayList<String>? = null,
    @SerializedName("toxicityLevel") var toxicityLevel: String? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("waterRequirement") var waterRequirement: WaterRequirement? = null
)
