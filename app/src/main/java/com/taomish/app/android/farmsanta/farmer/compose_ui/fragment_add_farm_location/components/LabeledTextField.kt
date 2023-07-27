package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun LabeledTextField(
    modifier: Modifier,
    text: () -> String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    textColor: Color = Color.Cameron,
    minChars: Int = 0,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    hasFocus: MutableState<Boolean>,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    val spacing = LocalSpacing.current
    val shape = MaterialTheme.shapes.small
    Column(
        modifier = modifier
            .background(Color.White)
    ) {

        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = textStyle
        )

        FarmerTextField(
            modifier = Modifier
                .padding(top = spacing.extraSmall)
                .fillMaxWidth()
                .background(
                    color = if (text().length >= minChars) Color.Cameron.copy(alpha = .1f)
                    else Color.LightGray.copy(alpha = .2f),
                    shape = shape
                ),
            boxHeight = 40.dp,
            text = text,
            onValueChange = onValueChange,
            placeholderText = label,
            textStyle = textStyle,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            hasFocus = hasFocus,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

    }
}