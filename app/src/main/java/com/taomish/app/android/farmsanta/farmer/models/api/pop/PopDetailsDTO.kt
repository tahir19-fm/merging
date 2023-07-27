package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar

data class PopDetailsDTO(
    @SerializedName("approvalAssignBy") var approvalAssignBy: String? = null,
    @SerializedName("croppingProcessDto") var croppingProcessDto: CroppingProcessDto? = null,
    @SerializedName("cropNutritions") var cropNutritions: List<CropNutrition>? = null,
    @SerializedName("cultivars") var cultivars: List<Cultivar>? = null,
    @SerializedName("deficiencyDto") var deficiencyDto: List<DeficiencyDto>? = null,
    @SerializedName("diseases") var diseases: List<Disease>? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("fungicides") var fungicides: List<Fungicide>? = null,
    @SerializedName("insecticides") var insecticides: List<Insecticide>? = null,
    @SerializedName("insects") var insects: List<Insect>? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("popDetails") var popDto: PopDto? = null,
    @SerializedName("predators") var predators: List<Predator>? = null,
    @SerializedName("seedTreatments") var seedTreatments: List<SeedTreatment>? = null,
    @SerializedName("trapCrops") var trapCrops: List<TrapCrop>? = null,
    @SerializedName("weedicides") var weedicides: List<Weedicide>? = null,
    @SerializedName("weeds") var weeds: List<Weed>? = null,
    @SerializedName("climateDtos") var climateDtos: List<ClimateDto>? = null,
    @SerializedName("harvestingDescription") var harvestingDescription: String? = null,
    @SerializedName("postHarvestingDescription") var postHarvestingDescription: String? = null,
)
