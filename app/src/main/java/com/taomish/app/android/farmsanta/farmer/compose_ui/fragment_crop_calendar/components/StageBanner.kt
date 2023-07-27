package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@Composable
fun StageBanner(
    bannerId: Int?,
    stageName: String?,
    stageWeek: String?,
    startEndDate: String?,
    isCurrentTask: Boolean
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(top = spacing.small)
    ) {

        Image(
            painter = painterResource(id = bannerId.getStageIcon()),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Banner"
        )

        if (isCurrentTask) {
            Ongoing()
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = spacing.small, vertical = spacing.medium)
        ) {
            Text(
                text = stageName.notNull(),
                color = Color.White,
                style = MaterialTheme.typography.caption
            )

            Text(
                text = stageWeek.notNull(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )

            Text(
                text = startEndDate.notNull(),
                color = Color.White,
                style = MaterialTheme.typography.overline
            )
        }
    }
}