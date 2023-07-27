package com.taomish.app.android.farmsanta.farmer.models.api.tags

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class TrendingTags (

    @PrimaryKey
    var id: Int = -1,

    @SerializedName("tags")
    var tags: List<String>? = null
)