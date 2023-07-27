package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class Weed(

    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("crops") var crops: ArrayList<String>? = null,
    @SerializedName("cultivarGroups") var cultivarGroups: List<String>? = null,
    @SerializedName("cultivars") var cultivars: ArrayList<String>? = null,
    @SerializedName("culturalControl") var culturalControl: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("favourableConditions") var favourableConditions: String? = null,
    @SerializedName("localName") var localName: String? = null,
    @SerializedName("photos") var photos: ArrayList<Photo>? = null,
    @SerializedName("regions") var regions: ArrayList<String>? = null,
    @SerializedName("scientificName") var scientificName: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("territories") var territories: ArrayList<String>? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("weedType") var weedType: String? = null
)
