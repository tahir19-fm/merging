package com.taomish.app.android.farmsanta.farmer.models.api.fertilizer

import com.google.gson.annotations.SerializedName

data class FertilizerCropsResponse(
    @SerializedName("FIELD_CROP") var FIELDCROP: ArrayList<FertilizerCrop>? = null,
    @SerializedName("OILSEED_CROP") var OILSEEDCROP: ArrayList<FertilizerCrop>? = null,
    @SerializedName("FRUIT_CROP") var FRUITCROP: ArrayList<FertilizerCrop>? = null,
    @SerializedName("VEGITABLE_CORP") var VEGITABLECORP: ArrayList<FertilizerCrop>? = null,
)
