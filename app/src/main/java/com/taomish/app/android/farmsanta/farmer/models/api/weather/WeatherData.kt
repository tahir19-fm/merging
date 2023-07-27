package com.taomish.app.android.farmsanta.farmer.models.api.weather

import com.google.gson.annotations.SerializedName

data class WeatherData(

	@field:SerializedName("thunderStrom")
	val thunderStrom: List<ThunderStromItem?>? = null,

	@field:SerializedName("rain")
	val rain: List<RainItem?>? = null,

	@field:SerializedName("irrigation")
	val irrigation: String? = null,

	@field:SerializedName("harvesting")
	val harvesting: String? = null,

	@field:SerializedName("weatherDetails")
	val weatherDetails: WeatherAll? = null,

	@field:SerializedName("weeding")
	val weeding: String? = null,

	@field:SerializedName("pesticideApplication")
	val pesticideApplication: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Rain(

	@field:SerializedName("1h")
	val jsonMember1h: Double? = null
)

data class ThunderStromItem(

	@field:SerializedName("dayType")
	val dayType: String? = null,

	@field:SerializedName("thunderStrom")
	val thunderStrom: Boolean? = null
)


data class RainItem(

	@field:SerializedName("dayType")
	val dayType: String? = null,

	@field:SerializedName("rain")
	val rain: Boolean? = null,

	@field:SerializedName("rainType")
	val rainType: String? = null
)
