package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {

    val crops = listOf("Rice", "Wheat", "Corn")
    val processes = listOf("IPM", "Harvesting", "Nutrition")

    val cropDropDownExpanded = mutableStateOf(false)
    val cropSelected = mutableStateOf(crops[0])
    val processDropDownExpanded = mutableStateOf(false)
    val processSelected = mutableStateOf(processes[0])
}