package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class Molecule(
    @SerializedName("moleculeName") var moleculeName: String? = null,
    @SerializedName("moleculePercentageByVolume") var moleculePercentageByVolume: Double? = null,
    @SerializedName("moleculeStructure") var moleculeStructure: String? = null
)
