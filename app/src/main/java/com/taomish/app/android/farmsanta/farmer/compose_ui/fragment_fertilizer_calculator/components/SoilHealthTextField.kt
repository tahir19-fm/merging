package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerLevel
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import java.util.*


@OptIn(ExperimentalTextApi::class)
@Composable
fun SoilHealthTextField(
    text: MutableState<String>,
    level: MutableState<Int?>? = null,
    @StringRes placeHolderId: Int,
    unit: String,
    low: Double = 0.0,
    medium: Double = 0.0,
    high: Double = 0.0,
    hasFocus: MutableState<Boolean>,
    isPhValue: Boolean = false,
    onDone: (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    var colors by remember { mutableStateOf(listOf(Color.Transparent, Color.Transparent)) }
    var validationMessageId by remember { mutableStateOf(R.string.low) }
    val focusManager = LocalFocusManager.current

    text.value.let {
        try {
            val input = it.toDoubleOrNull() ?: 0.0
            if (input > 0.0) {
                when {
                    input < low -> {
                        colors =
                            listOf(Color.OutrageousOrange, Color.OutrageousOrange.copy(alpha = .4f))
                        validationMessageId = if (isPhValue) R.string.acidic else R.string.low
                        level?.postValue(FertilizerLevel.LOW)
                    }
                    input in low..high -> {
                        colors =
                            listOf(Color.OrangePeel, Color.OrangePeel.copy(alpha = .4f))
                        validationMessageId = if (isPhValue) R.string.normal else R.string.medium
                        level?.postValue(FertilizerLevel.MEDIUM)
                    }
                    input > high -> {
                        colors = listOf(Color.Thunderbird, Color.Thunderbird.copy(alpha = .4f))
                        validationMessageId = if (isPhValue) R.string.alkaline else R.string.high
                        level?.postValue(FertilizerLevel.HIGH)
                    }
                    else -> {
                        level?.postValue(null)
                    }
                }
            } else {
                level?.postValue(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.small, vertical = spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FarmerTextField(
            modifier = Modifier.fillMaxWidth(.6f),
            text = { text.value },
            onValueChange = { text.postValue(it) },
            placeholderText = str(id = placeHolderId),
            backgroundColor = Color.White,
            hasFocus = hasFocus,
            textStyle = MaterialTheme.typography.body2,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = if (onDone != null) ImeAction.Done else ImeAction.Next
            ),
            trailingIcon = {
                if (text.value.isNotEmpty()) {
                    FertilizerTag(
                        modifier = Modifier.fillMaxWidth(.55f),
                        text = str(id = placeHolderId),
                        textStyle = MaterialTheme.typography.overline
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
                onDone = { onDone?.invoke() }
            )
        )

        Text(
            text = unit,
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(start = spacing.extraSmall)
                .fillMaxWidth(.5f)
        )

        if (text.value.isNotEmpty() && (text.value.toDoubleOrNull() ?: 0.0) > 0.0) {
            Text(
                text = str(id = validationMessageId).uppercase(Locale.ENGLISH),
                style = MaterialTheme.typography.caption.copy(
                    brush = Brush.horizontalGradient(colors)
                ),
                modifier = Modifier
                    .padding(start = spacing.extraSmall)
                    .fillMaxWidth()
            )
        }
    }
}