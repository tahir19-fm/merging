package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.utils.getLocationName
import com.taomish.app.android.farmsanta.farmer.utils.isEmpty
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

class FarmScoutingViewModel : ViewModel() {

    val selectedScouting: MutableState<FarmScouting?> = mutableStateOf(null)
    val advisory: MutableState<Advisory?> = mutableStateOf(null)

    val locationName: MutableState<String> = mutableStateOf("")
    val plantPartsIssue = mutableStateOf("N/A")
    val chatTextField: MutableState<String> = mutableStateOf("")
    val textError: MutableState<Boolean> = mutableStateOf(false)
    val searchText = mutableStateOf("")

    val scouting: MutableState<Map<Boolean, List<FarmScouting>>> = mutableStateOf(emptyMap())
    val crops: MutableState<Map<String, CropMaster>> = mutableStateOf(emptyMap())
    val stages: MutableState<Map<String, CropStage>> = mutableStateOf(emptyMap())

    val cropDtoList = mutableListOf<CropMaster>()
    val plantPartDtoList = mutableListOf<GlobalIndicatorDTO>()
    val growthStageDtoList = mutableListOf<CropStage>()

    val cropList = mutableStateListOf<String>()

    val selectedCropDto: MutableLiveData<CropMaster?> = MutableLiveData(null)

    val plantList = mutableStateListOf<String>()
    val selectedPlantDto: MutableState<GlobalIndicatorDTO?> = mutableStateOf(null)
    val growthStageList = mutableStateListOf<String>()
    val selectedStageDto: MutableState<CropStage?> = mutableStateOf(null)

    val queryTitleText: MutableState<String> = mutableStateOf("")
    val cropExpanded: MutableState<Boolean> = mutableStateOf(false)
    val crop: MutableState<String> = mutableStateOf("")
    val plantExpanded: MutableState<Boolean> = mutableStateOf(false)
    val plantText: MutableState<String> = mutableStateOf("")
    val growthStageExpanded: MutableState<Boolean> = mutableStateOf(false)
    val growthStageText: MutableState<String> = mutableStateOf("")
    val queryText: MutableState<String> = mutableStateOf("")
    val captureImage: MutableLiveData<Boolean> = MutableLiveData()
    val imagePath: MutableLiveData<String> = MutableLiveData()
    val imageFiles = mutableStateOf<List<String>>(emptyList())

    var growthStageDataList = mutableStateListOf<CropStage>()


    fun validate(context: Context) : Boolean {
        if (imageFiles.value.isEmpty()) {
            context.showToast(R.string.image_error)
            return false
        }
        if (queryTitleText.isEmpty()) {
            context.showToast(R.string.title_error)
            return false
        }
        if (crop.isEmpty()) {
            context.showToast(R.string.crop_error)
            return false
        }
        if (plantText.isEmpty()) {
            context.showToast(R.string.plant_error)
            return false
        }
        if (growthStageText.isEmpty()) {
            context.showToast(R.string.growth_stage_error)
            return false
        }
        if (queryText.isEmpty()) {
            context.showToast(R.string.query_details_error)
            return false
        }
        return true
    }

    fun loadData(context: Context) {
        var parts = ""
        selectedScouting.value?.images?.forEach {
            parts += (if (it.plantPart.isNullOrEmpty()) "" else "${it.plantPart}, ")
        }
        if (parts.isNotEmpty()) {
            parts = parts.dropLast(2)
            plantPartsIssue.postValue(parts)
        }
        val landId = (selectedScouting.value?.landId ?: "0")
        var land: Land? = null
        if (landId != "0") {
            land = getLand(landId)
            locationName.value = land?.farmLocation ?: "N/A"
        }

        if (locationName.value.isEmpty()) {
            locationName.value = if (land?.latitude?.isFinite() == true
                && land.longitude?.isFinite() == true
            )
                LatLng(land.latitude, land.longitude).getLocationName(context)
            else LatLng(
                DataHolder.getInstance().lastKnownLocation?.latitude ?: 0.0,
                DataHolder.getInstance().lastKnownLocation?.longitude ?: 0.0
            ).getLocationName(context)
        }
    }

    private fun getLand(landId: String): Land? {
        DataHolder.getInstance().selectedFarmer?.lands?.forEach {
            if (it?.landId == landId) {
                return it
            }
        }

        return null
    }

    fun clearData() {
        onCleared()
    }

}