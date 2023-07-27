package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@Composable
fun CroppingPeriod(weeksRange: String?, dateRange: String?) {
    val spacing = LocalSpacing.current
    Text(
        text = weeksRange.notNull(),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body2,
        modifier = Modifier.padding(top = spacing.small)
    )

    Text(
        text = dateRange.notNull(),
        color = Color.White,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(bottom = spacing.small)
    )
}