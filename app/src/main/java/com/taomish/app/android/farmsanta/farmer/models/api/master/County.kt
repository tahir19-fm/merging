package com.taomish.app.android.farmsanta.farmer.models.api.master

import com.google.gson.annotations.SerializedName

data class County(
    @SerializedName("countyName") var countyName: String,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("languageId") var languageId: Int? = null,
    @SerializedName("regionId") var regionId: String? = null,
    @SerializedName("sequencId") var sequencId: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null
)
