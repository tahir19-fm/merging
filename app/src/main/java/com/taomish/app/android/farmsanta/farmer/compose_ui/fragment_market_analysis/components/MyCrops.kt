package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun MyCrops(
    crops: List<String>
) {
    val spacing = LocalSpacing.current
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        items(crops) { crop ->
            CropChip(
                title = crop,
                leadingIconId = R.drawable.ic_crop,
                leadingIconColor = Color.White,
                backgroundColor = Color.Cameron,
                contentColor = Color.White
            )
        }
    }
}