package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun <T : Any> FertilizerCropSelection(
    crops: List<T>,
    getCropName: (T) -> String,
    onSelect: (T) -> Unit,
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.small, bottom = spacing.extraLarge.plus(spacing.medium))
    ) {

        item {
            FlowRow {
                crops.forEach { crop ->
                    SelectableChip(
                        text = getCropName(crop),
                        textStyle = MaterialTheme.typography.caption,
                        isSelected = false,
                        unselectedBackgroundColor = Color.Limeade,
                        unselectedContentColor = Color.White,
                        onClick = { onSelect(crop) }
                    )
                }
            }
        }
    }
}