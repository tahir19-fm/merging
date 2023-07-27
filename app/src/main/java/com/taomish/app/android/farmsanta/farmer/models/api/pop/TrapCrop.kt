package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class TrapCrop(

    @SerializedName("createdBy") var createdBy: String,
    @SerializedName("createdTimestamp") var createdTimestamp: String,
    @SerializedName("crops") var crops: ArrayList<String>? = null,
    @SerializedName("diseases") var diseases: ArrayList<String>? = null,
    @SerializedName("insects") var insects: ArrayList<String>? = null,
    @SerializedName("methodOfPlanting") var methodOfPlanting: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("photo") var photo: ArrayList<Photo>? = null,
    @SerializedName("regions") var regions: ArrayList<String>? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("tenantId") var tenantId: String? = null,
    @SerializedName("territories") var territories: ArrayList<String>? = null,
    @SerializedName("updatedBy") var updatedBy: String? = null,
    @SerializedName("updatedTimestamp") var updatedTimestamp: String? = null,
    @SerializedName("uuid") var uuid: String? = null
)
