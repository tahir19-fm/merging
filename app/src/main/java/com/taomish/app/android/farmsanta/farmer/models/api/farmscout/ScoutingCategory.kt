package com.taomish.app.android.farmsanta.farmer.models.api.farmscout

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity
data class ScoutingCategory(

	@PrimaryKey(autoGenerate = true) var id: Int = -1,

	@field:SerializedName("List")
	val list: CategoryList? = null
)

@Entity
data class CategoryList(

	@TypeConverters(CategoryConverter::class)
	@field:SerializedName("item")
	val item: List<CategoryItem?>? = null
)

@Entity
data class CategoryItem(

	@field:SerializedName("sequenceNumber")
	var sequenceNumber: Int? = null,

	@field:SerializedName("globalIndicatorUuid")
	var globalIndicatorUuid: String? = null,

	@field:SerializedName("groupName")
	var groupName: String? = null,

	@field:SerializedName("updatedBy")
	var updatedBy: String? = null,

	@field:SerializedName("createdBy")
	var createdBy: String? = null,

	@field:SerializedName("createdTimestamp")
	var createdTimestamp: String? = null,

	@field:SerializedName("tenantId")
	var tenantId: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@PrimaryKey
	@field:SerializedName("uuid")
	var uuid: String = "",

	@field:SerializedName("updatedTimestamp")
	var updatedTimestamp: String? = null,

	@field:SerializedName("status")
	var status: String? = null
)

class CategoryConverter: Converters<CategoryItem>()

abstract class Converters<T> {
	var gson = Gson()

	@TypeConverter
	fun stringToList(data: String?): List<T> {
		if (data == null) {
			return listOf()
		}
		val itemType = object : TypeToken<List<T>>() {}.type
		return gson.fromJson(data, itemType)
	}

	@TypeConverter
	fun listToString(someObject: List<T>?): String {
		return gson.toJson(someObject)
	}
}

