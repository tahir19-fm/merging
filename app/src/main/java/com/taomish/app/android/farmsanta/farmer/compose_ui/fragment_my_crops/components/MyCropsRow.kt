package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun MyCropsRow(
    crops: List<CropMaster>,
    selectedCrop: MutableState<CropMaster?>,
    onSelect: (CropMaster) -> Unit,
) {
    val spacing = LocalSpacing.current
    LazyRow(
        modifier = Modifier.padding(spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(crops) { crop ->
            CropChip(
                title = crop.cropName,
                leadingIconId = R.drawable.ic_crop,
                leadingIconColor = if (selectedCrop.value?.cropName == crop.cropName) Color.White else Color.Cameron,
                backgroundColor = if (selectedCrop.value?.cropName == crop.cropName) Color.Cameron else Color.White,
                contentColor = if (selectedCrop.value?.cropName == crop.cropName) Color.White else Color.Cameron,
                onClick = { onSelect(crop) }
            )
        }
    }
}