package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components.SortType
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.pop.*
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark

class POPViewModel : ViewModel() {

    val selected = mutableStateOf(0)
    val pop = mutableStateOf<PopDto?>(null, policy = neverEqualPolicy())
    val popDetails = mutableStateOf<PopDetailsDTO?>(null)
    var selectedPopIndex = -1
    val searchText = mutableStateOf("")
    val pops: MutableState<List<PopDto>> = mutableStateOf(emptyList(), neverEqualPolicy())
    val bookmarkedPops = mutableStateOf<List<PopDto>>(emptyList())
    val popBookMarks = mutableStateOf<List<BookMark>>(emptyList())
    val isRefreshing = mutableStateOf(false)
    var crops by mutableStateOf<Map<String, CropMaster>>(emptyMap())
    val selectedInsect = mutableStateOf<Insect?>(null)
    val selectedDisease = mutableStateOf<Disease?>(null)
    val selectedWeed = mutableStateOf<Weed?>(null)
    var selectedIPMItemIndex = 0
    var lastSortType = SortType.By_Date
    var rowStates = emptyList<MutableState<Boolean>>()
}