package com.taomish.app.android.farmsanta.farmer.models.api.pop

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import java.text.SimpleDateFormat
import java.util.*

data class PopDto(
    @SerializedName("author") @Expose val author: String? = null,
    @SerializedName("content") @Expose val content: String? = null,
    @SerializedName("createdBy") @Expose val createdBy: String,
    @SerializedName("createdTimestamp") @Expose val createdTimestamp: String,
    @SerializedName("crop") @Expose val crop: String? = null,
    @SerializedName("cultivarGroups ") @Expose val cultivarGroups: List<String>? = null,
    @SerializedName("cultivars ") @Expose val cultivars: List<String>? = null,
    @SerializedName("endDate") @Expose val endDate: String,
    @SerializedName("photos") @Expose val photos: List<Photo>? = null,
    @SerializedName("regions") @Expose val regions: List<String>? = null,
    @SerializedName("startDate") @Expose val startDate: String,
    @SerializedName("status") @Expose val status: String? = null,
    @SerializedName("tags") @Expose val tags: List<String>? = null,
    @SerializedName("tenantId") @Expose val tenantId: String,
    @SerializedName("territories") @Expose val territories: List<String>? = null,
    @SerializedName("title") @Expose val title: String?,
    @SerializedName("updatedBy") @Expose val updatedBy: String,
    @SerializedName("updatedTimestamp") @Expose val updatedTimestamp: String? = null,
    @SerializedName("uuid") @Expose val uuid: String,
    @SerializedName("bookmarked") @Expose var bookmarked: Boolean = false,
    @SerializedName("firstName") @Expose var firstName: String? = null,
    @SerializedName("lastName") @Expose var lastName: String? = null,
    @SerializedName("profileImage") @Expose val profileImage: String? = null,
    var cropName: String? = null,
    var bookmarkedUUID: String? = null,
) {
    fun getDate(dateTime: String? = updatedTimestamp ?: createdTimestamp): Date {
        var dt = dateTime
        if ((dateTime?.length ?: 1) > 23) {
            dt = dateTime?.dropLast(dateTime.length - 23)
        }

        val inputDateFormat =
            if (dateTime?.contains("T") == true) "yyyy-MM-dd'T'HH:mm:ss.SSS" else "yyyy-MM-dd HH:mm:ss"
        val outputFormat = "dd MMMM, yyyy' | 'HH:mm"
        val input = SimpleDateFormat(inputDateFormat, Locale.ENGLISH)
        val output = SimpleDateFormat(outputFormat, Locale.ENGLISH)
        if (dt != null) {
            try {
                return input.parse(dt)!!   // format output
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return Date()
    }

    fun getCropNameById(): String {
        return DataHolder.getInstance().cropMasterMap[crop]?.cropName ?: "No Name"
    }
}


