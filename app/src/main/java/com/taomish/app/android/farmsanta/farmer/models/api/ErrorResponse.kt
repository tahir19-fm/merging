package com.taomish.app.android.farmsanta.farmer.models.api

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String? = null,

    @SerializedName("code")
    val code: String? = null
)