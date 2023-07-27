package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropCalendar
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropStageCalendar
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import java.time.LocalDate

class CropCalendarViewModel : ViewModel() {

    var currentTaskIndex = 0
    var visibleData= mutableStateOf(false)
    val selectedCrop = mutableStateOf<CropMaster?>(null)
    val selectedCropCalendar = mutableStateOf<CropCalendar?>(null)
    val stagesMap = mutableStateMapOf<String, GlobalIndicatorDTO>()
    val date = mutableStateOf<LocalDate?>(null)
    val day = mutableStateOf("DD")
    val month = mutableStateOf("MM")
    val year = mutableStateOf("YYYY")
    val buttonEnabled = mutableStateOf(false)
    val updateButtonEnabled = mutableStateOf(false)
    val allCropDivisions = mutableStateListOf<Pair<String, String>>()
    val allCropsByDivisions = mutableStateOf<Map<String, List<CropMaster>>>(emptyMap())
    val allCrops = mutableStateListOf<CropMaster>()
    val userCrops = mutableStateListOf<CropMaster>()
    val cropCalendarCrops = mutableStateListOf<CropMaster>()

    val cropCalendars = mutableStateListOf<CropCalendar>()
    val cropStages = mutableStateOf<List<CropStageCalendar>>(emptyList())
    val selectedStage = mutableStateOf<CropStageCalendar?>(null)
    var selectedStageIndex = 0
    var selectedWeekIndex = mutableStateOf(0)
    var selectedOperationIndex = mutableStateOf(0)

    fun validate() {
        buttonEnabled.postValue(selectedCrop.value != null && date.value != null)
    }
    fun check() {
        updateButtonEnabled.postValue(date.value != null)
    }
    fun setUpdateButtonEnabled() {
            updateButtonEnabled.postValue(false)
    }
}