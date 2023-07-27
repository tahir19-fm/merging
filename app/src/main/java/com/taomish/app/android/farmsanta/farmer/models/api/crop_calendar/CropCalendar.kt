package com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar

import com.google.gson.annotations.SerializedName

data class CropCalendar(
    @SerializedName("cropId") var cropId: String? = null,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("stageId") var stageId: Int = 0,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("userId") var userId: String? = null
)
