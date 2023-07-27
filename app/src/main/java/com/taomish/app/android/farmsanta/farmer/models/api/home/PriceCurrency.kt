package com.taomish.app.android.farmsanta.farmer.models.api.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PriceCurrency(
    @SerializedName("currency") @Expose var currency: String? = null,
    @SerializedName("value") @Expose var value: Int,
)
