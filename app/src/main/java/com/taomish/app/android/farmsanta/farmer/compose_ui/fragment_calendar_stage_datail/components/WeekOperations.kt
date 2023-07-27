package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.Task


@Composable
fun BoxScope.WeekOperations(tasks: List<Task>) {
    val spacing = LocalSpacing.current
    val colors = listOf(
        Color.Apple,
        Color.Limeade,
        Color.Citron,
        Color.Bahia,
        Color.ButterCup
    )
    LazyRow(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(horizontal = spacing.small)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.small)
    ) {

        itemsIndexed(tasks) { index, task ->
            CroppingStageIcon(
                task = task,
                backgroundColor = colors[index % (colors.size - 1)]
            )
        }
    }
}