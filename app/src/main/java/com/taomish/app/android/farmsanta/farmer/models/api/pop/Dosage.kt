package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class Dosage(
    @SerializedName("unit") var unit: Float? = null,
    @SerializedName("uom") var uom: String? = null
)
