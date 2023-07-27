package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_confirm_otp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun OtpCharTextField(
    modifier: Modifier = Modifier,
    digit: MutableState<String>,
    hasFocus: MutableState<Boolean>,
    focusManager: FocusManager,
    index: Int,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    val width = ((LocalConfiguration.current.screenWidthDp - 88) / 6).dp
    val textFieldDefaults = TextFieldDefaults.textFieldColors(
        cursorColor = Color.Cameron,
        backgroundColor = Color.LightGray.copy(alpha = .2f),
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent
    )


    OutlinedTextField(
        modifier = modifier
            .padding(vertical = spacing.extraSmall)
            .background(color = Color.LightGray.copy(.15f), shape = shape)
            .height(48.dp)
            .width(width)
            .onFocusChanged { hasFocus.postValue(it.hasFocus) },
        value = digit.value,
        onValueChange = {
            if (it.isEmpty()) {
                digit.postValue("")
                if (index != 0) {
                    focusManager.moveFocus(FocusDirection.Previous)
                }
            } else {
                if (('0'..'9').any { c -> it.last() == c }) {
                    digit.postValue(it.last().toString())
                    if (index == 5) {
                        focusManager.clearFocus()
                    } else {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                }
            }
        },
        shape = shape,
        colors = textFieldDefaults,
        textStyle = MaterialTheme.typography.caption.copy(textAlign = TextAlign.Center),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}