package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NutriSourceViewModel :  ViewModel() {
    val searchText = mutableStateOf("")
    val categories = listOf("Fertilizers", "Seeds", "Agrochemicals")
    val products = listOf(
        listOf("NPK", "Urea", "Ammonium sulphate", "Falcon DAP", "Potassium sulphate", "Powder"),
        listOf("Chia Seeds", "Rajgira Seeds", "Sunflower Seeds", "Pumpkin Seeds", "Basil Seeds", "Hemp Seeds"),
        listOf("Tafethion", "Krithion", "Mit-505", "Mitkill", "King Mite", "Fosmite", "Deviastra", "Vithion"),
    )
    val selectedCategory = mutableStateOf<String?>(null)
    val selectedProduct = mutableStateOf<String?>(null)
    val selectedProducts = mutableStateOf(products[0])
}