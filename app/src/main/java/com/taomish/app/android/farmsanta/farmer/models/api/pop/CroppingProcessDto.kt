package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class CroppingProcessDto(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("crop") var crop: String? = null,
    @SerializedName("territory") var territory: String? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("createDate") var createDate: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("processlist") var processlist: ArrayList<Process>? = null,
    @SerializedName("languageId") var languageId: Int? = null,
    @SerializedName("modifiedDate") var modifiedDate: String? = null
)
