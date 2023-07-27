package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.ImageNoChip(text: String) {
    val spacing = LocalSpacing.current
    androidx.compose.material.Chip(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = spacing.small, end = spacing.small),
        onClick = { },
        colors = ChipDefaults.chipColors(
            backgroundColor = Color.Gray.copy(alpha = .4f),
            contentColor = Color.White
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.overline)
    }
}