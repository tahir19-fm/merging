package com.taomish.app.android.farmsanta.farmer.models.api.fertilizer

import com.google.gson.annotations.SerializedName

data class FertilizerFinalReport(

    @field:SerializedName("area")
    val area: Double? = null,

    @field:SerializedName("totalBags")
    val totalBags: Double? = null,

    @field:SerializedName("cropId")
    val cropId: String? = null,

    @field:SerializedName("cropName")
    val cropName: String? = null,

    @field:SerializedName(value = "potassium", alternate = ["mopFR"])
    val potassium: Potassium? = null,

    @field:SerializedName(value = "npk", alternate = ["ureaFR"])
    val npk: Npk? = null,

    @field:SerializedName("lastploughing")
    val lastploughing: String? = null,

    @field:SerializedName("timeOfsowing")
    val timeOfsowing: String? = null,

    @field:SerializedName(value = "vermicompost", alternate = ["boneMealFR"])
    val vermicompost: Vermicompost? = null,

    @field:SerializedName(value = "nitrogenous", alternate = ["nkcFR"])
    val nitrogenous: Nitrogenous? = null,

    @field:SerializedName("daysAfter25")
    val daysAfter25: String? = null,

    @field:SerializedName(value = "fym", alternate = ["fymFR"])
    val fym: Fym? = null,

    @field:SerializedName(value = "zinc", alternate = ["zinkSulphateFR"])
    val zinc: Zinc? = null,

    @field:SerializedName(value = "boron", alternate = ["boraxFR"])
    val boron: Boron? = null,

    @field:SerializedName(value = "phosphorus", alternate = ["dapFR"])
    val phosphorus: Phosphorus? = null,

    @field:SerializedName("daysAfter40")
    val daysAfter40: String? = null
)

data class Phosphorus(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)

data class Boron(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)

data class Potassium(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)

data class Nitrogenous(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)

data class Fym(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)

data class Npk(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)

data class Vermicompost(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)

data class Zinc(

    @field:SerializedName(value = "unit", alternate = ["unit2"])
    val unit: String? = null,

    @field:SerializedName(value = "quantity", alternate = ["totalQuantity"])
    val quantity: Double? = null,

    @field:SerializedName("bags")
    val bags: Double? = null,

    @field:SerializedName("fertilizerName")
    val fertilizerName: String? = null
)
