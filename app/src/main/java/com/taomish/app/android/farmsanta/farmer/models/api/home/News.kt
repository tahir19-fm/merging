package com.taomish.app.android.farmsanta.farmer.models.api.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id") @Expose  var id: String,
    @SerializedName("userId") @Expose  val userId: Any,
@SerializedName("lastUpdated") @Expose  val lastUpdated: Any,
@SerializedName("title") @Expose  val title: String,
@SerializedName("image") @Expose  val image: String,
@SerializedName("content") @Expose  val content: String,
@SerializedName("url") @Expose  val url: String)
