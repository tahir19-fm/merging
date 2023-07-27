package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.utils.maizeRustImages

class DiseasesViewModel : ViewModel() {

    val crops = listOf("Mango", "Rice", "Wheat", "Corn")
    val selected = mutableStateOf(crops[0])
    val selectedImages = mutableStateOf(maizeRustImages)
}