package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_scouting_image_details.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import dashedBorder
import com.taomish.app.android.farmsanta.farmer.R


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectedImagesCard(
    getImages: () -> List<String>,
    onAddClicked: () -> Unit,
    onCloseClicked: (Int) -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val pagerState = rememberPagerState()
    val images = getImages()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = if (images.isEmpty()) Color.RiceFlower else Color.Transparent)
            .dashedBorder(
                width = .8.dp,
                color = Color.Gray,
                shape = shape,
                on = 4.dp,
                off = 4.dp
            )
    ) {
        if (images.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                count = images.size
            ) { page ->
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = spacing.large),
                    painter = rememberAsyncImagePainter(model = images[page]),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
            Chip(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = spacing.small, bottom = spacing.large + spacing.small),
                text = "${pagerState.currentPage + 1} / ${images.size}",
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
                    .padding(top = spacing.small, end = spacing.small)
                    .background(Color.Gray, CircleShape)
                    .clip(CircleShape)
                    .clickable { onCloseClicked(pagerState.currentPage) }
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
            Text(
                text = "+ ${str(id = R.string.add_image)}",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        onAddClicked.invoke()
                    }
            )
        }
    }
}