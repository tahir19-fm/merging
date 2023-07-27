package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class Process(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("processId") var processId: String? = null,
    @SerializedName("sequenceId") var sequenceId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null
)
