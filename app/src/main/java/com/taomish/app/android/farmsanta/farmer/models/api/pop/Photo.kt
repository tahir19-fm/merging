package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Photo (
    @SerializedName("caption") @Expose val caption: String,
    @SerializedName("fileName") @Expose val fileName: String,
    @SerializedName("photoId") @Expose val photoId: String,
    @SerializedName("sequenceId") @Expose val sequenceId: Int = 0
)