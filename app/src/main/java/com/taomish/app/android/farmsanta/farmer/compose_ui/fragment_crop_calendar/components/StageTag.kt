package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Apple
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing

@Composable
fun StageTag(tag: String, backgroundColor: Color = Color.Apple) {
    val spacing = LocalSpacing.current
    val brush = Brush.horizontalGradient(listOf(backgroundColor, backgroundColor.copy(alpha = .5f)))
    Text(
        text = tag,
        color = Color.White,
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .padding(start = spacing.small, top = spacing.small)
            .background(brush = brush, shape = CircleShape)
            .padding(spacing.extraSmall)
    )
}