package com.taomish.app.android.farmsanta.farmer.models.api.gender

import com.google.gson.annotations.SerializedName

data class GenderResponse(

	@field:SerializedName("List")
	val list: GenderList? = null
)

data class GenderItem(

	@field:SerializedName("sequenceNumber")
	val sequenceNumber: Int? = null,

	@field:SerializedName("globalIndicatorUuid")
	val globalIndicatorUuid: String? = null,

	@field:SerializedName("groupName")
	val groupName: String? = null,

	@field:SerializedName("updatedBy")
	val updatedBy: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("createdTimestamp")
	val createdTimestamp: String? = null,

	@field:SerializedName("tenantId")
	val tenantId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("updatedTimestamp")
	val updatedTimestamp: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class GenderList(

	@field:SerializedName("item")
	val item: List<GenderItem>? = null
)
