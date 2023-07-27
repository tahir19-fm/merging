package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun PeriodsRow(
    periods: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val spacing = LocalSpacing.current
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = spacing.small,
                vertical = spacing.extraSmall
            )
    ) {
        itemsIndexed(periods) { index, period ->
            Text(
                text = period,
                color = if (selectedIndex == index) Color.Cameron else Color.Gray,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(spacing.extraSmall)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = { onSelect(index) }
                    )
            )
        }
    }
}