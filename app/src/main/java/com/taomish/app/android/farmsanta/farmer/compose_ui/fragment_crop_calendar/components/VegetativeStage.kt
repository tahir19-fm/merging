package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun VegetativeStage() {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(top = spacing.small)
    ) {

        RemoteImage(
            modifier = Modifier.fillMaxSize(),
            imageLink = "",
            contentScale = ContentScale.FillBounds,
            error = R.drawable.ic_vegetative_stage_banner
        )

        Upcoming()

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = spacing.small, vertical = spacing.medium)
        ) {
            Text(
                text = str(id = R.string.pre_seeding_stage),
                color = Color.White,
                style = MaterialTheme.typography.caption
            )

            Text(
                text = str(id = R.string.on_four_weeks),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )

            Text(
                text = "12 Aug- 8 Sept",
                color = Color.White,
                style = MaterialTheme.typography.overline
            )
        }
    }
}