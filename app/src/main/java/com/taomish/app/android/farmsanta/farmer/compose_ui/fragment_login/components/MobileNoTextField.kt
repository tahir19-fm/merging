package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_login.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.hbb20.CountryCodePicker
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun MobileNoTextField(
    text: MutableState<String>,
    countryCodePicker: CountryCodePicker,
    maxChars: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    onDone: (() -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    BackHandler(hasFocus.value) { focusManager.clearFocus() }

    Column(
        modifier = Modifier
            .fillMaxWidth(.8f)
            .padding(vertical = spacing.medium)
    ) {
        Text(
            text = str(id = R.string.mobile_number),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2
        )

        FarmerTextField(
            modifier = Modifier
                .padding(top = spacing.small)
                .fillMaxWidth(),
            backgroundColor = Color.LightGray.copy(alpha = .2f),
            boxHeight = 48.dp,
            text = { text.value },
            onValueChange = {
                if (it.length <= maxChars) {
                    text.postValue(it)
                }
            },
            placeholderText = "",
            textStyle = MaterialTheme.typography.caption,
            hasFocus = hasFocus,
            enabled = enabled,
            leadingIcon = { FarmerCountryCodePicker(countryCodePicker) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onDone?.invoke()
            })
        )

    }
}

@Composable
fun FarmerCountryCodePicker(countryCodePicker: CountryCodePicker) {
    AndroidView(
        modifier = Modifier,
        factory = { countryCodePicker }
    )
}