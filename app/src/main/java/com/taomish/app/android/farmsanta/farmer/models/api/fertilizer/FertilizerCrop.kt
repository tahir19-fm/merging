package com.taomish.app.android.farmsanta.farmer.models.api.fertilizer

import com.google.gson.annotations.SerializedName

data class FertilizerCrop(
    @SerializedName("fertilizerType") var fertilizerType: String,
    @SerializedName("cropName") var cropName: String,
    @SerializedName("cropId") var cropId: String
)
