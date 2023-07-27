package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.notification.AdvisoryTag
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage

class CropAdvisoryViewModel : ViewModel() {

    var isFiltered by  mutableStateOf(false)

    val cropAdvisories = mutableStateListOf<CropAdvisory>()
    val cropsMap = mutableStateOf<Map<String, CropMaster>>(emptyMap())
    val growthStages = mutableStateOf<Map<String, GlobalIndicatorDTO>>(emptyMap())
    val advisoryTags = mutableStateOf<Map<String, AdvisoryTag>>(emptyMap())

    val filterCrops = mutableStateListOf<CropMaster>()
    val filterGrowthStages = mutableStateListOf<GlobalIndicatorDTO>()
    val filterAdvisoryTags = mutableStateListOf<AdvisoryTag>()

    val text: MutableState<String> = mutableStateOf("")
    val selectedAdvisory: MutableState<CropAdvisory?> = mutableStateOf(null)
    val selectedCrops = mutableStateListOf<String>()
    val selectedAdvisories = mutableStateListOf<String>()
    val selectedGrowthStages = mutableStateListOf<String>()

    fun clearFilter() {
        isFiltered = false
        filterCrops.clear()
        filterGrowthStages.clear()
        filterAdvisoryTags.clear()
        cropAdvisories.clear()
        cropAdvisories.addAll(DataHolder.getInstance().cropAdvisoryArrayList)
    }

}