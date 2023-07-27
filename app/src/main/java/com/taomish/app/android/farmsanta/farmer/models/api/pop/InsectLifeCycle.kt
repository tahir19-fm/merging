package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class InsectLifeCycle(
    @SerializedName("characteristics") var characteristics: String? = null,
    @SerializedName("lifecycleStage") var lifecycleStage: String? = null,
    @SerializedName("photo") var photo: Photo? = null,
    @SerializedName("symptomsOfAttack") var symptomsOfAttack: String? = null
)
