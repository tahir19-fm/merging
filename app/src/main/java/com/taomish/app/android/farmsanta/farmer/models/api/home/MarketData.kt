package com.taomish.app.android.farmsanta.farmer.models.api.home

import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("price") var price: PriceValue? = null,
    @SerializedName("priceDate") var priceDate: String? = null
)
