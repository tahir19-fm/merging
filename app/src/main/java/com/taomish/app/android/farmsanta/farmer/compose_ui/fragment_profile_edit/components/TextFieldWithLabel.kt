package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun TextFieldWithLabel(
    modifier: Modifier,
    text: () -> String,
    onValueChange: (String) -> Unit,
    minChars: Int = 0,
    label: String,
    isMandatory: Boolean,
    showOptionalText: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    hasFocus: MutableState<Boolean>,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier.padding(vertical = spacing.small)) {
        Row {
            Text(
                text = "$label ",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = when {
                    isMandatory -> stringResource(id = R.string.mandatory)
                    showOptionalText -> stringResource(id = R.string.optional)
                    else -> ""
                },
                color = if (isMandatory) Color.Red else Color.LightGray,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )
        }

        FarmerTextField(
            modifier = Modifier
                .padding(top = spacing.extraSmall)
                .fillMaxWidth(),
            backgroundColor = if (text().length >= minChars) Color.Cameron.copy(alpha = .1f)
            else Color.LightGray.copy(alpha = .2f),
            text = text,
            onValueChange = onValueChange,
            placeholderText = label,
            textStyle = MaterialTheme.typography.body2,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            hasFocus = hasFocus,
            enabled = enabled,
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

    }
}