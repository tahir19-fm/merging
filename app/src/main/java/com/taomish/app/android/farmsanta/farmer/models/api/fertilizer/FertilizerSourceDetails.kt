package com.taomish.app.android.farmsanta.farmer.models.api.fertilizer

import com.google.gson.annotations.SerializedName

data class FertilizerSourceDetails(

	@field:SerializedName("phosphorusP")
	val phosphorusP: Double? = null,

	@field:SerializedName("lime")
	val lime: Double? = null,

	@field:SerializedName("nitroN")
	val nitroN: Double? = null,

	@field:SerializedName("fertilizerName")
	val fertilizerName: String? = null,

	@field:SerializedName("zincZN")
	val zincZN: Double? = null,

	@field:SerializedName("sulphurS")
	val sulphurS: Double? = null,

	@field:SerializedName("cost_kg")
	val costKg: Double? = null,

	@field:SerializedName("photassiumK")
	val photassiumK: Double? = null,

	@field:SerializedName("fertilizerType")
	val fertilizerType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("borronB")
	val borronB: Double? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("territory")
	val territory: String? = null
)
