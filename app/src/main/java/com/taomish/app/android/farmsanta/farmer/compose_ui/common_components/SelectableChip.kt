package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OutrageousOrange


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectableChip(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    isSelected: Boolean,
    selectedBackgroundColor: Color = Color.OutrageousOrange,
    unselectedBackgroundColor: Color = Color.LightGray.copy(alpha = .2f),
    selectedContentColor: Color = Color.White,
    unselectedContentColor: Color = Color.Gray,
    onClick: (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    Chip(
        modifier = modifier.padding(horizontal = spacing.extraSmall),
        onClick = { onClick?.invoke() },
        shape = CircleShape,
        colors = ChipDefaults.chipColors(
            backgroundColor = if (isSelected) selectedBackgroundColor else unselectedBackgroundColor,
            contentColor = if (isSelected) selectedContentColor else unselectedContentColor
        )
    ) {
        Text(text = text, style = textStyle)
    }
}