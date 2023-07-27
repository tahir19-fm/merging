package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CropCalendarChip(
    crop: CropMaster,
    onSelect: ((CropMaster) -> Unit)? = null,
    isSelected: Boolean = false
) {
    val spacing = LocalSpacing.current
    Chip(
        modifier = Modifier.padding(horizontal = spacing.extraSmall),
        onClick = { onSelect?.invoke(crop) },
        shape = CircleShape,
        colors = ChipDefaults.chipColors(
            backgroundColor = if (isSelected) Color.Cameron else Color.White,
            contentColor = if (isSelected) Color.White else Color.Gray
        )
    ) {
        Text(text = crop.cropName ?: "", style = MaterialTheme.typography.overline)
    }
}