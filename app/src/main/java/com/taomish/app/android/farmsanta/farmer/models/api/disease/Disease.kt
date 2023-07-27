package com.taomish.app.android.farmsanta.farmer.models.api.disease

import com.google.gson.annotations.SerializedName

data class Disease(

	@field:SerializedName("favourableConditions")
	var favourableConditions: String? = null,

	@field:SerializedName("updatedBy")
	val updatedBy: String? = null,

	@field:SerializedName("regions")
	val regions: List<String?>? = null,

	@field:SerializedName("scientificName")
	var scientificName: String? = null,

	@field:SerializedName("createdTimestamp")
	val createdTimestamp: String? = null,

	@field:SerializedName("languageId")
	val languageId: Int? = null,

	@field:SerializedName("preventiveMeasures")
	var preventiveMeasures: String? = null,

	@field:SerializedName("cultivars")
	val cultivars: List<String?>? = null,

	@field:SerializedName("crops")
	val crops: List<String?>? = null,

	@field:SerializedName("symptomsOfAttack")
	var symptomsOfAttack: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("updatedTimestamp")
	val updatedTimestamp: String? = null,

	@field:SerializedName("photos")
	val photos: List<PhotosItem?>? = null,

	@field:SerializedName("culturalMechanicalControl")
	var culturalMechanicalControl: String? = null,

	@field:SerializedName("localName")
	var localName: String? = null,

	@field:SerializedName("cultivarGroups")
	var cultivarGroups: List<String?>? = null,

	@field:SerializedName("territories")
	val territories: List<String?>? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("tenantId")
	val tenantId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PhotosItem(

	@field:SerializedName("fileName")
	val fileName: String? = null,

	@field:SerializedName("photoId")
	val photoId: String? = null,

	@field:SerializedName("caption")
	val caption: String? = null,

	var isSelected: Boolean = false,
)
