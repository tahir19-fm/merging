package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class DeficiencyDto(
    @SerializedName("biologicalControl") var biologicalControl: String? = null,
    @SerializedName("chemicalControl") var chemicalControl: String? = null,
    @SerializedName("nutrient") var nutrient: String? = null,
    @SerializedName("nutrientId") var nutrientId: String? = null,
    @SerializedName("photos") var photos: ArrayList<Photo>? = arrayListOf(),
    @SerializedName("preventiveMeasures") var preventiveMeasures: String? = null,
    @SerializedName("symptomsOfDeficiency") var symptomsOfDeficiency: String? = null
)
