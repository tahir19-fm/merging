package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_other_farmer_pops.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchChip(tag: String, isSelected: Boolean, onSelect: () -> Unit) {
    val spacing = LocalSpacing.current
    Chip(
        modifier = Modifier.padding(horizontal = spacing.extraSmall),
        onClick = { onSelect() },
        shape = CircleShape,
        colors = ChipDefaults.chipColors(
            backgroundColor = if (isSelected) Color.Cameron else Color.LightGray.copy(.3f),
            contentColor = if (isSelected) Color.White else Color.Cameron
        )
    ) {
        Text(text = tag, style = MaterialTheme.typography.caption)
    }
}