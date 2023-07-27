package com.taomish.app.android.farmsanta.farmer.models.api.master

import com.google.gson.annotations.SerializedName

data class CountryMaster(
    @SerializedName("createdTimestamp")
    val createdTimestamp: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("iso")
    val iso: String,

    @SerializedName("iso3")
    val iso3: String,

    @SerializedName("languageCode")
    val languageCode: String? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("niceName")
    val niceName: String,

    @SerializedName("numCode")
    val numCode: Int,

    @SerializedName("phoneCode")
    val phoneCode: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("updatedTimestamp")
    val updatedTimestamp: String

)
