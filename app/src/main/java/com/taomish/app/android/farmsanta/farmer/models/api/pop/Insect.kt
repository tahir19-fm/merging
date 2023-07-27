package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class Insect(
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("crops") var crops: ArrayList<String>? = arrayListOf(),
    @SerializedName("cultivarGroups") var cultivarGroups: List<String>? = arrayListOf(),
    @SerializedName("cultivars") var cultivars: ArrayList<String>? = arrayListOf(),
    @SerializedName("culturalMechanicalControl") var culturalMechanicalControl: String? = null,
    @SerializedName("favourableConditions") var favourableConditions: String? = null,
    @SerializedName("insectLifeCycles") var insectLifeCycles: List<InsectLifeCycle>? = null,
    @SerializedName("localName") var localName: String? = null,
    @SerializedName("photos") var photos: ArrayList<Photo>? = null,
    @SerializedName("preventiveMeasures") var preventiveMeasures: String? = null,
    @SerializedName("regions") var regions: ArrayList<String>? = null,
    @SerializedName("scientificName") var scientificName: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("symptomsOfAttack") var symptomsOfAttack: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("territories") var territories: ArrayList<String>? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null
)
