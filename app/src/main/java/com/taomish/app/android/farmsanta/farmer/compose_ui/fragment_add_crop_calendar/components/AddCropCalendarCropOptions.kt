package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun AddCropCalendarCropOptions(
    title: String,
    options: List<CropMaster>,
    onSelect: (CropMaster) -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small)
        )


        FlowRow(verticalGap = spacing.small, horizontalGap = spacing.small) {
            options.forEach { crop ->
                CropCalendarChip(crop = crop, onSelect = onSelect)
            }
        }
    }
}