package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*


@Composable
fun BoxScope.Ongoing(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    val brush = Brush.horizontalGradient(
        listOf(Color.OrangePeel, Color.OrangePeel.copy(alpha = .5f))
    )

    Text(
        text = str(id = R.string.ongoing),
        color = Color.White,
        style = MaterialTheme.typography.caption,
        modifier = modifier
            .padding(top = spacing.medium)
            .align(Alignment.TopEnd)
            .background(
                brush = brush,
                shape = RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50)
            )
            .padding(vertical = spacing.extraSmall, horizontal = spacing.small)
    )
}

@Composable
fun BoxScope.Upcoming(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    val brush = Brush.horizontalGradient(
        listOf(Color.Cameron, Color.Cameron.copy(alpha = .5f))
    )

    Text(
        text = str(id = R.string.upcoming),
        color = Color.White,
        style = MaterialTheme.typography.caption,
        modifier = modifier
            .padding(top = spacing.medium)
            .align(Alignment.TopEnd)
            .background(
                brush = brush,
                shape = RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50)
            )
            .padding(vertical = spacing.extraSmall, horizontal = spacing.small)
    )
}