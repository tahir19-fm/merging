package com.taomish.app.android.farmsanta.farmer.models.api.master

import com.google.gson.annotations.SerializedName

data class UOMType(
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("createdTimestamp") var createdTimestamp: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("uomType") var uomType: String? = null,
    @SerializedName("uomConversion") var uomConversion: String? = null,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String? = null,
    @SerializedName("status") var status: String? = null
)