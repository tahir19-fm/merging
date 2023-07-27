package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class Predator(
    @SerializedName("createdBy") var createdBy: String,
    @SerializedName("createdTimestamp") var createdTimestamp: String,
    @SerializedName("crops") var crops: List<String>? = null,
    @SerializedName("cultivarGroups") var cultivarGroups: List<String>? = null,
    @SerializedName("cultivars") var cultivars: List<String>? = null,
    @SerializedName("diseases") var diseases: List<String>? = null,
    @SerializedName("insects") var insects: List<String>? = null,
    @SerializedName("photos") var photos: List<Photo>? = null,
    @SerializedName("predatorName") var predatorName: String? = null,
    @SerializedName("regions") var regions: List<String>? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("territories") var territories: List<String>? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null
)
