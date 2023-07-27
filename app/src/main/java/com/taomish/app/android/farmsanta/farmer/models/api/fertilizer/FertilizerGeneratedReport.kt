package com.taomish.app.android.farmsanta.farmer.models.api.fertilizer

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class FertilizerGeneratedReport(

    @field:SerializedName("nitrogenousFertilizer")
    val nitrogenousFertilizer: Int? = null,

    @field:SerializedName("phosphorusFertilizer")
    val phosphorusFertilizer: Int? = null,

    @field:SerializedName("finalReport")
    val finalReport: String? = null,

    @field:SerializedName("boronFertilizer")
    val boronFertilizer: Int? = null,

    @field:SerializedName("zincLevelZN")
    val zincLevelZN: Int? = null,

    @field:SerializedName("sulphurS")
    val sulphurS: Double? = null,

    @field:SerializedName("npkFertilizer")
    val npkFertilizer: Int? = null,

    @field:SerializedName("sulphurLevelS")
    val sulphurLevelS: Double? = null,

    @field:SerializedName("borronLevelB")
    val borronLevelB: Int? = null,

    @field:SerializedName("nitrogenLevelN")
    val nitrogenLevelN: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("testReportAvailable")
    val testReportAvailable: Boolean? = null,

    @field:SerializedName("borronB")
    val borronB: Double? = null,

    @field:SerializedName("potassiumFertilizer")
    val potassiumFertilizer: Double? = null,

    @field:SerializedName("area")
    val area: Double? = null,

    @field:SerializedName("phosphorusP")
    val phosphorusP: Double? = null,

    @field:SerializedName("cropId")
    val cropId: String? = null,

    @field:SerializedName("cropPriority")
    val cropPriority: Int? = null,

    @field:SerializedName("photassiumLevelK")
    val photassiumLevelK: Double? = null,

    @field:SerializedName("createdTimestamp")
    val createdTimestamp: String? = null,

    @field:SerializedName("ageOfPlant")
    val ageOfPlant: Double? = null,

    @field:SerializedName("zincZN")
    val zincZN: Double? = null,

    @field:SerializedName("zincFertilizer")
    val zincFertilizer: Double? = null,

    @field:SerializedName("createdBy")
    val createdBy: String? = null,

    @field:SerializedName("photassiumK")
    val photassiumK: Double? = null,

    @field:SerializedName("potentialHydrogenPH")
    val potentialHydrogenPH: Double? = null,

    @field:SerializedName("potentialHydrogenLevelPH")
    val potentialHydrogenLevelPH: Int? = null,

    @field:SerializedName("phosphorusLevelP")
    val phosphorusLevelP: Int? = null,

    @field:SerializedName("cropType")
    val cropType: Int? = null,

    @field:SerializedName("nitrogenN")
    val nitrogenN: Double? = null,
) {
    fun getFinalReport(): FertilizerFinalReport =
        Gson().fromJson(finalReport, FertilizerFinalReport::class.java)
}

