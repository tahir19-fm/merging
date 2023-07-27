package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun AddCropsFruits(
    crops: List<CropMaster>,
    onSelect: (CropMaster) -> Unit
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.small)
    ) {

        item {

            Text(
                text = "Fruits",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = spacing.medium)
            )

            FlowRow(verticalGap = spacing.small) {
                crops.forEach { crop ->
                    AddCropChip(
                        crop = crop,
                        showCloseIcon = false,
                        onSelect = { onSelect(crop) }
                    )
                }
            }
        }
    }
}