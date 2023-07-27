package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCropChip(
    crop: CropMaster,
    backgroundColor: Color = Color.Cameron,
    contentColor: Color = Color.White,
    showCloseIcon: Boolean = true,
    onDelete: ((CropMaster) -> Unit)? = null,
    onSelect: ((CropMaster) -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    val borderStroke = BorderStroke(width = .3.dp, color = Color.Cameron)
    Chip(
        modifier = Modifier.padding(horizontal = spacing.extraSmall),
        onClick = { onSelect?.invoke(crop) },
        shape = CircleShape,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        border = borderStroke
    ) {
        Text(text = crop.cropName ?: "N/A", style = MaterialTheme.typography.caption)
        if (showCloseIcon) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .size(16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) { onDelete?.invoke(crop) }
            )
        }
    }
}