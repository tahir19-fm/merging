package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components.CropUI
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop


@Composable
fun Vegetables(onSelect: (String) -> Unit) {
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

        val crop = Crop()
        item {
            FlowRow {
                vegetables.forEach { vegetable ->
                    CropUI(
                        crop = crop,
                        name = vegetable,
                        showClose = false,
                        onSelect = { onSelect(vegetable) }
                    )
                }
            }
        }
    }
}