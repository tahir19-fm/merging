package com.taomish.app.android.farmsanta.farmer.models.api.master

import com.google.gson.annotations.SerializedName

data class Village(
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("sequencId") var sequencId: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("subcountyId") var subcountyId: Int? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("villageName") var villageName: String? = null
)
