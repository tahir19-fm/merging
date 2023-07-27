package com.taomish.app.android.farmsanta.farmer.models.api.fertilizer

import com.google.gson.annotations.SerializedName

data class GenerateFertilizerReportPayload(

	@field:SerializedName("nitrogenousFertilizer")
	val nitrogenousFertilizer: Int? = null,

	@field:SerializedName("phosphorusFertilizer")
	val phosphorusFertilizer: Int? = null,

	@field:SerializedName("boronFertilizer")
	val boronFertilizer: Int? = null,

	@field:SerializedName("zincLevelZN")
	val zincLevelZN: Int? = null,

	@field:SerializedName("npkFertilizer")
	val npkFertilizer: Int? = null,

	@field:SerializedName("sulphurS")
	val sulphurS: Double? = null,

	@field:SerializedName("sulphurLevelS")
	val sulphurLevelS: Int? = null,

	@field:SerializedName("borronLevelB")
	val borronLevelB: Int? = null,

	@field:SerializedName("nitrogenLevelN")
	val nitrogenLevelN: Int? = null,

	@field:SerializedName("testReportAvailable")
	val testReportAvailable: Boolean = false,

	@field:SerializedName("borronB")
	val borronB: Double? = null,

	@field:SerializedName("potassiumFertilizer")
	val potassiumFertilizer: Int? = null,

	@field:SerializedName("area")
	val area: Double? = null,

	@field:SerializedName("phosphorusP")
	val phosphorusP: Double? = null,

	@field:SerializedName("cropId")
	val cropId: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("cropPriority")
	val cropPriority: Int? = null,

	@field:SerializedName("photassiumLevelK")
	val photassiumLevelK: Int? = null,

	@field:SerializedName("ageOfPlant")
	val ageOfPlant: Double? = null,

	@field:SerializedName("zincZN")
	val zincZN: Double? = null,

	@field:SerializedName("zincFertilizer")
	val zincFertilizer: Int? = null,

	@field:SerializedName("photassiumK")
	val photassiumK: Double? = null,

	@field:SerializedName("potentialHydrogenLevelPH")
	val potentialHydrogenLevelPH: Int? = null,

	@field:SerializedName("potentialHydrogenPH")
	val potentialHydrogenPH: Double? = null,

	@field:SerializedName("phosphorusLevelP")
	val phosphorusLevelP: Int? = null,

	@field:SerializedName("cropType")
	val cropType: Int? = null,

	@field:SerializedName("nitrogenN")
	val nitrogenN: Double? = null
)
