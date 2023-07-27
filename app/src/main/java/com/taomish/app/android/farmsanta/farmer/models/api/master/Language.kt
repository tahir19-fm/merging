package com.taomish.app.android.farmsanta.farmer.models.api.master

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("id")
    val id: Int,

    @SerializedName("lang")
    val lang: String,

    @SerializedName("locale")
    val locale: String,

    @SerializedName("name")
    val name: String
)
