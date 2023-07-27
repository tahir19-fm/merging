package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import dashedBorder


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectFertilizerImages(
    list: List<String>,
    onAddClicked: () -> Unit,
    onCloseClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .height(200.dp)
            .background(color = if (list.isEmpty()) Color.RiceFlower else Color.Transparent)
            .dashedBorder(
                width = .8.dp,
                color = Color.Limeade,
                shape = shape,
                on = 4.dp,
                off = 4.dp
            )
    ) {
        if (list.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                count = list.size
            ) { page ->
                RemoteImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = spacing.large),
                    imageLink = list[page],
                    contentScale = ContentScale.FillBounds,
                    error = R.mipmap.img_default_pop
                )
            }
            Chip(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = spacing.small, bottom = spacing.large + spacing.small),
                text = "${list.size} / ${pagerState.currentPage + 1}",
                textPadding = spacing.small,
                textStyle = MaterialTheme.typography.overline,
                backgroundColor = Color.Gray.copy(alpha = .2f)
            )

            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .clip(CircleShape)
                    .padding(top = spacing.small, end = spacing.small)
                    .clickable { onCloseClicked() }
            )


            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(56.dp)
                    .background(color = Color.White.copy(alpha = .4f), shape = CircleShape)
                    .padding(spacing.small)
                    .border(width = 1.dp, color = Color.Cameron, shape = CircleShape)
                    .background(color = Color.White, shape = CircleShape)
                    .padding(spacing.small)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = onAddClicked
                    )
            )
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_scouting_add_camera),
                    contentDescription = null,
                    tint = Color.Limeade,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(onClick = onAddClicked)
                )

                Text(
                    text = str(id = R.string.take_picture),
                    color = Color.Limeade,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}