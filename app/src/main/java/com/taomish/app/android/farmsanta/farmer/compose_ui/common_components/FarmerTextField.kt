package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FarmerTextField(
    modifier: Modifier = Modifier,
    text: () -> String,
    onValueChange: (String) -> Unit,
    validate: (String) -> Boolean = { true },
    placeholderText: String,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    textColor: Color = Color.Black,
    enabled: Boolean = true,
    maxLines: Int = 1,
    singleLine: Boolean = maxLines == 1,
    isError: Boolean = false,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    backgroundColor: Color = Color.LightGray.copy(alpha = .2f),
    boxHeight: Dp = 40.dp,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    hasFocus: MutableState<Boolean>? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    val colors = TextFieldDefaults.textFieldColors(
        backgroundColor = backgroundColor,
        cursorColor = Color.Cameron,
        textColor = textColor
    )
    val interactionSource = remember { MutableInteractionSource() }

    val brush = SolidColor(colors.cursorColor(false).value)
    BasicTextField(
        value = text(),
        onValueChange = { if (validate(it)) onValueChange(it) },
        textStyle = textStyle,
        modifier = modifier
            .background(
                color = colors.backgroundColor(enabled).value,
                shape = shape
            )
            .indicatorLine(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = colors,
                focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
            )
            .height(boxHeight)
            .onFocusChanged { hasFocus?.postValue(it.hasFocus) },
        interactionSource = interactionSource,
        cursorBrush = brush,
        enabled = enabled,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = text(),
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            visualTransformation = VisualTransformation.None,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            placeholder = {
                Text(
                    text = placeholderText,
                    color = textColor,
                    style = textStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = maxLines
                )
            },
            interactionSource = interactionSource,
            // keep horizontal paddings but change the vertical
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 0.dp, bottom = 0.dp
            )
        )
    }
}