package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.himanshoe.charty.line.model.LineData
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto

@SuppressLint("MutableCollectionMutableState")
class MyCropsViewModel : ViewModel() {

    val crops = DataHolder.getInstance().cropArrayList

    val chartData = listOf(
        LineData("6 AM", 562f),
        LineData("7 AM", 759f),
        LineData("8 AM", 485f),
        LineData("9 AM", 987f),
        LineData("10 AM", 1506f),
        LineData("11 AM", 2500f),
        LineData("12 PM", 1854f),
        LineData("1 PM", 1256f)
    )

    var selectedCrop = mutableStateOf<CropMaster?>(null)
    val cropAdvisoriesArrayList = mutableStateOf<ArrayList<CropAdvisory>?>(null)
    var popDtoArrayList = mutableStateOf<ArrayList<PopDto>?>(null)
    var farmScoutingArrayList = mutableStateOf<ArrayList<FarmScouting?>?>(null)
}