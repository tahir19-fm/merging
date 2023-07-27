package com.taomish.app.android.farmsanta.farmer.models.api.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginWithReference (
        @SerializedName("referenceNo") @Expose val referenceNo: String,
        @SerializedName("mobileNo") @Expose val mobileNo: String
)