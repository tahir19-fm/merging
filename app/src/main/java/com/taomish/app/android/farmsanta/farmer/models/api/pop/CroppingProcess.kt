package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class CroppingProcess(
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("popId") var popId: String? = null,
    @SerializedName("file") var file: Photo? = null
)
