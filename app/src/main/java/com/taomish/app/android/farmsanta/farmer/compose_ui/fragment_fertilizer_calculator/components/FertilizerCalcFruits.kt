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
fun FertilizerCalcFruits(onSelect: (String) -> Unit) {
    val spacing = LocalSpacing.current
    val fruits = listOf(
        "Apple", "Mango", "Orange", "Strawberry", "Grapes", "Pomegranate",
        "Pear", "Avocado", "Banana", "Blueberry", "Lemon", "Kiwi", "Pineapple"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.small)
    ) {

        item {
            FlowRow {
                fruits.forEach { fruit ->
                    SelectableChip(
                        text = fruit,
                        textStyle = MaterialTheme.typography.caption,
                        isSelected = false,
                        unselectedBackgroundColor = Color.Limeade,
                        unselectedContentColor = Color.White,
                        onClick = { onSelect(fruit) }
                    )
                }
            }
        }
    }
}