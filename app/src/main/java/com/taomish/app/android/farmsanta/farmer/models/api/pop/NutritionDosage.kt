package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class NutritionDosage(
    @SerializedName("growthStage") var growthStage: String? = null,
    @SerializedName("nitrogen") var nitrogen: Double? = null,
    @SerializedName("phosphorus") var phosphorus: Double? = null,
    @SerializedName("potassium") var potassium: Double? = null,
    @SerializedName("zinc") var zinc: Double? = null,
    @SerializedName("sulphur") var sulphur: Double? = null
)
