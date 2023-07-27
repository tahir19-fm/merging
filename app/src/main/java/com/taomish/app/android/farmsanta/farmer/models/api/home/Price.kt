package com.taomish.app.android.farmsanta.farmer.models.api.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("uuid") @Expose var uuid: String,
    @SerializedName("createdBy") @Expose val createdBy: String? = null,
    @SerializedName("updatedBy") @Expose val updatedBy: String? = null,
    @SerializedName("createdTimestamp") @Expose val createdTimestamp: String? = null,
    @SerializedName("updatedTimestamp") @Expose val updatedTimestamp: String? = null,
    @SerializedName("startDate") @Expose val startDate: String? = null,
    @SerializedName("endDate") @Expose val endDate: String? = null,
    @SerializedName("tenantId") @Expose val tenantId: String? = null,
    @SerializedName("territory") @Expose val territory: List<String>? = null,
    @SerializedName("region") @Expose val region: List<String>? = null,
    @SerializedName("commodityType") @Expose val commodityType: String? = null,
    @SerializedName("commodityName") @Expose val commodityName: String? = null,
    @SerializedName("productName") @Expose val productName: String? = null,
    @SerializedName("commodityDetails") @Expose val commodityDetails: String? = null,
    @SerializedName("location") @Expose val location: String? = null,
    @SerializedName("tickerCode") @Expose val tickerCode: String? = null,
    @SerializedName("status") @Expose val status: String? = null,
    @SerializedName("priceDate") @Expose val priceDate: String? = null,
    @SerializedName("previousPrice") @Expose val previousPrice: PreviousPrice? = null,
    @SerializedName("currentPrice") @Expose val currentPrice: CurrentPrice? = null,
    @SerializedName("priceCurrency") @Expose val priceCurrency: PriceCurrency? = null,
    @SerializedName("packaging") @Expose var packaging: Packaging? = null
)
