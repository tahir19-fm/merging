package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.SerializedName

data class ClimateDto(

	@field:SerializedName("harvestTemperature")
	val harvestTemperature: HarvestTemperature? = null,

	@field:SerializedName("regions")
	val regions: List<String?>? = null,

	@field:SerializedName("updatedBy")
	val updatedBy: String? = null,

	@field:SerializedName("annualRain")
	val annualRain: AnnualRain? = null,

	@field:SerializedName("createdTimestamp")
	val createdTimestamp: String? = null,

	@field:SerializedName("cropName")
	val cropName: String? = null,

	@field:SerializedName("tempUnit")
	val tempUnit: String? = null,

	@field:SerializedName("cultivars")
	val cultivars: List<String?>? = null,

	@field:SerializedName("averageRain")
	val averageRain: AverageRain? = null,

	@field:SerializedName("growingTemperature")
	val growingTemperature: GrowingTemperature? = null,

	@field:SerializedName("sowingTemperature")
	val sowingTemperature: SowingTemperature? = null,

	@field:SerializedName("updatedTimestamp")
	val updatedTimestamp: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("cultivarGroups")
	val cultivarGroups: List<String?>? = null,

	@field:SerializedName("territories")
	val territories: List<String?>? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("tenantId")
	val tenantId: String? = null,

	@field:SerializedName("humidity")
	val humidity: Humidity? = null,

	@field:SerializedName("remarks")
	val remarks: String? = null,

	@field:SerializedName("windVelocity")
	val windVelocity: WindVelocity? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AnnualRain(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null
)

data class AverageRain(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null
)

data class Humidity(

	@field:SerializedName("unit")
	val unit: Double? = null,

	@field:SerializedName("uom")
	val uom: String? = null
)

data class SowingTemperature(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null
)

data class HarvestTemperature(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null
)

data class WindVelocity(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null
)

data class GrowingTemperature(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null
)
