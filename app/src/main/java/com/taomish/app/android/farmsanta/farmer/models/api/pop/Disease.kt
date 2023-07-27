package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class Disease(
    @SerializedName("createdBy") var createdBy: String,
    @SerializedName("createdTimestamp") var createdTimestamp: String,
    @SerializedName("crops") var crops: List<String>? = listOf(),
    @SerializedName("cultivarGroups") var cultivarGroups: List<String>? = listOf(),
    @SerializedName("cultivars") var cultivars: List<String>? = listOf(),
    @SerializedName("culturalMechanicalControl") var culturalMechanicalControl: String? = null,
    @SerializedName("favourableConditions") var favourableConditions: String? = null,
    @SerializedName("localName") var localName: String? = null,
    @SerializedName("photos") var photos: List<Photo>? = listOf(),
    @SerializedName("preventiveMeasures") var preventiveMeasures: String? = null,
    @SerializedName("regions") var regions: List<String>? = listOf(),
    @SerializedName("scientificName") var scientificName: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("symptomsOfAttack") var symptomsOfAttack: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("territories") var territories: List<String>? = listOf(),
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null
)
