package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> OptionChip(
    item: T,
    getName: () -> String?,
    onSelect: (T) -> Unit,
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    val spacing = LocalSpacing.current
    Chip(
        modifier = Modifier.padding(horizontal = spacing.extraSmall),
        onClick = { onSelect(item) },
        shape = CircleShape,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Text(text = getName().notNull(), style = MaterialTheme.typography.overline)
    }
}