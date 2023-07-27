package com.taomish.app.android.farmsanta.farmer.models.api.fertilizer

import com.google.gson.annotations.SerializedName

data class FertilizerFruitDetails(

	@field:SerializedName("cropName")
	val cropName: String? = null,

	@field:SerializedName("boneMeal")
	val boneMeal: Double? = null,

	@field:SerializedName("muriateOfPotashMOP")
	val muriateOfPotashMOP: Double? = null,

	@field:SerializedName("zincSulphate")
	val zincSulphate: Double? = null,

	@field:SerializedName("boraxB")
	val boraxB: Double? = null,

	@field:SerializedName("neemKaranjCake")
	val neemKaranjCake: Double? = null,

	@field:SerializedName("priority")
	val priority: Double? = null,

	@field:SerializedName("diAluminiumPhosphatDAP")
	val diAluminiumPhosphatDAP: Double? = null,

	@field:SerializedName("limeL")
	val limeL: Double? = null,

	@field:SerializedName("planting")
	val planting: Double? = null,

	@field:SerializedName("procedureNotes")
	val procedureNotes: String? = null,

	@field:SerializedName("ureaU")
	val ureaU: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fym")
	val fym: Double? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("territory")
	val territory: String? = null,

	@field:SerializedName("areaPerPlant")
	val areaPerPlant: Double? = null
)
