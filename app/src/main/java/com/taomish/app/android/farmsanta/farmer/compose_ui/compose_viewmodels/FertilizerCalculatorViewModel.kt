package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.*
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

class FertilizerCalculatorViewModel : ViewModel() {


    var fertilizerGeneratedReport: FertilizerGeneratedReport? = null

    val fertilizerFruitDetails = mutableStateListOf<FertilizerFruitDetails>()
    val allFertilizerCrops =
        mutableStateOf<Map<Int, List<FertilizerCrop>>>(emptyMap(), policy = neverEqualPolicy())
    val allCropsList = mutableStateListOf<CropMaster>()
    val isFruitCropSelected by derivedStateOf { selectedCropType.value == 4 }
    val selectedCropTab = mutableStateOf(0)
    val selectedCropType = mutableStateOf<Int?>(0)
    var selectedCropName by mutableStateOf("")
    val fertilizerCropTypes = mutableStateListOf(
        FertilizerCropType(type = "FIELD_CROP", id = 1, label = "Field crop"),
        FertilizerCropType(type = "VEGETABLE_CORP", id = 2, label = "Vegetable crop"),
        FertilizerCropType(type = "OILSEED_CROP", id = 3, label = "Oilseed crop"),
        FertilizerCropType(type = "FRUIT_CROP", id = 4, label = "Fruit crop"),
        FertilizerCropType(type = "OTHER_CROP", id = 5, label = "Other crops")
    )
    val fertilizerSourceDetails = mutableStateListOf<FertilizerSourceDetails>()
    val fertilizerReportsSavedList = mutableStateListOf<FertilizerGeneratedReport>()
    val fertilizerCropsResponse = mutableStateOf<FertilizerCropsResponse?>(null)


    val hasReport = mutableStateOf(false)
    val hasFocus = mutableStateOf(false)
    val isKeyboardOpened = mutableStateOf(false)

    val nitrogenTextField = mutableStateOf("")
    val phosphorousTextField = mutableStateOf("")
    val potassiumTextField = mutableStateOf("")
    val zincTextField = mutableStateOf("")
    val boronTextField = mutableStateOf("")
    val sulphurTextField = mutableStateOf("")
    val pHTextField = mutableStateOf("")

    val nitrogenLevel = mutableStateOf<Int?>(null)
    val phosphorousLevel = mutableStateOf<Int?>(null)
    val potassiumLevel = mutableStateOf<Int?>(null)
    val zincLevel = mutableStateOf<Int?>(null)
    val boronLevel = mutableStateOf<Int?>(null)
    val sulphurLevel = mutableStateOf<Int?>(null)
    val pHLevel = mutableStateOf<Int?>(null)

    val selectedCrop = mutableStateOf<FertilizerCrop?>(null)
    val ageOfCropExpanded = mutableStateOf(false)
    val fruitCropExpanded = mutableStateOf(false)
    val fruitCropNameSelected = mutableStateOf("")
    val selectedFruitCrop = mutableStateOf<FertilizerFruitDetails?>(null)
    val ageOfCropSelected = mutableStateOf("")
    val cropArea = mutableStateOf("")
    val npkComplexDropDownExpanded = mutableStateOf(false)
    val npkComplexSelected = mutableStateOf(FertilizerSourceDetails())
    val nitrogenousDropDownExpanded = mutableStateOf(false)
    val nitrogenousSelected = mutableStateOf(FertilizerSourceDetails())
    val potassicDropDownExpanded = mutableStateOf(false)
    val potassicSelected = mutableStateOf(FertilizerSourceDetails())
    val zincDropDownExpanded = mutableStateOf(false)
    val zincSelected = mutableStateOf(FertilizerSourceDetails())
    val bronDropDownExpanded = mutableStateOf(false)
    val bronSelected = mutableStateOf(FertilizerSourceDetails())
    val phosphorousDropDownExpanded = mutableStateOf(false)
    val phosphorousSelected = mutableStateOf(FertilizerSourceDetails())
    val isSavedFertilizer = mutableStateOf(false)

    // Min max values of parameters
    val nitrogenHighLowLevels = Pair(281.0, 560.0)
    val phosphorousHighLowLevels = Pair(26.0, 60.0)
    val potassiumHighLowLevels = Pair(120.0, 280.0)
    val zincHighLowLevels = Pair(0.6, 1.0)
    val boronHighLowLevels = Pair(0.25, 0.5)
    val sulphurHighLowLevels = Pair(10.0, 20.0)
    val pHHighLowLevels = Pair(6.5, 7.5)

    fun clearValues() {
        hasReport.postValue(false)
        nitrogenTextField.postValue("")
        phosphorousTextField.postValue("")
        potassiumTextField.postValue("")
        zincTextField.postValue("")
        boronTextField.postValue("")
        sulphurTextField.postValue("")
        pHTextField.postValue("")
        selectedCrop.postValue(null)
        ageOfCropSelected.postValue("")
        selectedFruitCrop.postValue(null)
        fruitCropNameSelected.postValue("")
        fruitCropExpanded.postValue(false)
        cropArea.postValue("")
        npkComplexSelected.postValue(FertilizerSourceDetails())
        nitrogenousSelected.postValue(FertilizerSourceDetails())
        potassicSelected.postValue(FertilizerSourceDetails())
        zincSelected.postValue(FertilizerSourceDetails())
        bronSelected.postValue(FertilizerSourceDetails())
        nitrogenLevel.postValue(null)
        phosphorousLevel.postValue(null)
        potassiumLevel.postValue(null)
        zincLevel.postValue(null)
        boronLevel.postValue(null)
        sulphurLevel.postValue(null)
        pHLevel.postValue(null)
        fertilizerGeneratedReport = null
    }

    fun showFertilizerSchedule(): Boolean {
        return fertilizerGeneratedReport?.getFinalReport()?.lastploughing.isNullOrEmpty().not()
                || fertilizerGeneratedReport?.getFinalReport()?.timeOfsowing.isNullOrEmpty().not()
                || fertilizerGeneratedReport?.getFinalReport()?.daysAfter25.isNullOrEmpty().not()
                || fertilizerGeneratedReport?.getFinalReport()?.daysAfter40.isNullOrEmpty().not()
    }

    fun validate(context: Context) : Boolean {
        if (nitrogenTextField.value.isEmpty()) {
            context.showToast(R.string.nitrogen_validation_msg)
            return false
        }
        if (phosphorousTextField.value.isEmpty()) {
            context.showToast(R.string.phosphorus_validation_msg)
            return false
        }
        if (potassiumTextField.value.isEmpty()) {
            context.showToast(R.string.potassium_validation_msg)
            return false
        }
        if (zincTextField.value.isEmpty()) {
            context.showToast(R.string.zinc_validation_msg)
            return false
        }
        if (boronTextField.value.isEmpty()) {
            context.showToast(R.string.boron_validation_msg)
            return false
        }
        if (sulphurTextField.value.isEmpty()) {
            context.showToast(R.string.sulphur_validation_msg)
            return false
        }
        if (pHTextField.value.isEmpty()) {
            context.showToast(R.string.ph_validation_msg)
            return false
        }
        return true
    }

    fun validate() : Boolean {
        return npkComplexSelected.value.fertilizerName != null &&
                potassicSelected.value.fertilizerName != null &&
                zincSelected.value.fertilizerName != null &&
                bronSelected.value.fertilizerName != null &&
                phosphorousSelected.value.fertilizerName != null &&
                nitrogenousSelected.value.fertilizerName != null
    }
}