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
fun FertilizerCalcVegetables(onSelect: (String) -> Unit) {
    val spacing = LocalSpacing.current
    val vegetables = listOf(
        "Potato", "Tomato", "Brinjal", "Cabbage", "Radish", "Onion",
        "Bitter Gourd", "Lady’s finger", "Cauliflower", "Pumpkin", "Carrot", "Ginger", "Chilli"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.small)
    ) {

        item {
            FlowRow {
                vegetables.forEach { vegetable ->
                    SelectableChip(
                        text = vegetable,
                        textStyle = MaterialTheme.typography.caption,
                        isSelected = false,
                        unselectedBackgroundColor = Color.Limeade,
                        unselectedContentColor = Color.White,
                        onClick = { onSelect(vegetable) }
                    )
                }
            }
        }
    }
}