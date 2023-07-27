package com.taomish.app.android.farmsanta.farmer.models.api.home

import com.google.gson.annotations.SerializedName

data class MarketDto(
    @SerializedName("list") var list: List<MarketData>? = null,
    @SerializedName("percentage") var percentage: Double? = null
)
