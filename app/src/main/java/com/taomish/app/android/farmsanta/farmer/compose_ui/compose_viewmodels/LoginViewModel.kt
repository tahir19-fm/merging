package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val phoneNumber = mutableStateOf("")
    val countryCode = mutableStateOf("")

    fun validate() : Boolean {
        return phoneNumber.value.length >= 8
    }
}