package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun AllTabsContent(
    crops: List<CropMaster>,
    onSelect: (CropMaster) -> Unit
) {
    val spacing = LocalSpacing.current
    FlowRow(
        modifier = Modifier
            .padding(
                start = spacing.small,
                end = spacing.small,
                top = spacing.small,
                bottom = 72.dp
            )
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalGap = spacing.extraSmall
    ) {
        crops.forEach { crop ->
            AddCropChip(
                crop = crop,
                showCloseIcon = false,
                onSelect = { onSelect(crop) },
                backgroundColor = Color.White,
                contentColor = Color.Cameron
            )
        }
    }

}