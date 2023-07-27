package com.taomish.app.android.farmsanta.farmer.models.api.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Packaging(
    @SerializedName("unit") @Expose var unit: Double,
    @SerializedName("uom") @Expose var uom: String,
)
