package com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar

import com.google.gson.annotations.SerializedName

data class CropStageCalendar (
    @SerializedName("cropId") var cropId: String? = null,
    @SerializedName("stageList") var weeks: List<StageWeek> = emptyList(),
    @SerializedName("stageName") var stageName: String? = null,
    @SerializedName("stageStatus") var stageStatus: Int? = null,
    @SerializedName("stageWeek") var stageWeek: String? = null,
    @SerializedName("startEndDate") var startEndDate: String? = null,
    var isCurrentTask: Boolean = false
)
