package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScoutDetailImageCard(farmScouting: FarmScouting) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallCardShape
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(384.dp)
            .padding(spacing.medium)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape),
            count = farmScouting.images.size,
            itemSpacing = spacing.large
        ) { page ->

            val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                        farmScouting.images[page].image.toString()

            RemoteImage(
                modifier = Modifier
                    .fillMaxSize(),
                imageLink = imageLink,
                contentScale = ContentScale.FillBounds,
                error = R.mipmap.img_default_pop
            )
        }

        Chip(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = spacing.small, bottom = spacing.small),
            text = DateUtil().getDateMonthYearFormat(farmScouting.createdTimestamp),
            textStyle = MaterialTheme.typography.overline,
            backgroundColor = Color.Black.copy(alpha = .4f),
        )

        Chip(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = spacing.small, top = spacing.small),
            text = str(id = if (farmScouting.advisoryExist) R.string.query_solved else R.string.pending_query),
            textStyle = MaterialTheme.typography.caption,
            textPadding = spacing.small,
            backgroundColor = if (farmScouting.advisoryExist) Color.Cameron else Color.OrangePeel,
            leadingIcon = { if (farmScouting.advisoryExist) DoneIcon() }
        )


        if (farmScouting.images.isNotEmpty()) {
            Text(
                text = "${farmScouting.images.size} / ${pagerState.currentPage + 1}",
                color = Color.White,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = spacing.small, top = spacing.small)
            )
        }
    }
}

@Composable
fun DoneIcon() {
    val spacing = LocalSpacing.current
    Icon(
        painter = painterResource(id = R.drawable.ic_check),
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.padding(start = spacing.small)
    )
}