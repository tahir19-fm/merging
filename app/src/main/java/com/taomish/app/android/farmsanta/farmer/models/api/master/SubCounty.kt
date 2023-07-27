package com.taomish.app.android.farmsanta.farmer.models.api.master

import com.google.gson.annotations.SerializedName

data class SubCounty(
    @SerializedName("id") var id: Int,
    @SerializedName("subcountyName") var subcountyName: String,
    @SerializedName("countyId") var countyId: Int,
    @SerializedName("sequencId") var sequencId: Int? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("languageId") var languageId: Int? = null
)
