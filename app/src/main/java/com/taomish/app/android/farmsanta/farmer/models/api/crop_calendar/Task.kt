package com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("oprationDescription") var oprationDescription: String? = null,
    @SerializedName("oprationName") var oprationName: String? = null,
    @SerializedName("oprationType") var oprationType: String? = null,
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("taskImages") var taskImages: ArrayList<String>? = null,
    @SerializedName("taskSequance") var taskSequance: Int? = null
)
