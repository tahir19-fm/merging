package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto

@Suppress("MutableCollectionMutableState")
class MyBookmarksPopsViewModel : ViewModel() {

    val popsList = mutableStateOf<ArrayList<PopDto>?>(null)
    val searchText = mutableStateOf("")
}