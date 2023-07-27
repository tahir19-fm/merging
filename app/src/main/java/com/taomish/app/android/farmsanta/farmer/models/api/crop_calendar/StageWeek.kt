package com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar

import com.google.gson.annotations.SerializedName

data class StageWeek(
    @SerializedName("cropId") var cropId: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("languageId") var languageId: Int? = null,
    @SerializedName("stageName") var stageName: String? = null,
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("tasklist") var tasklist: List<Task>? = null,
    @SerializedName("weekInfo") var weekInfo: Int? = null,
    var isCurrentTask: Boolean = false
)