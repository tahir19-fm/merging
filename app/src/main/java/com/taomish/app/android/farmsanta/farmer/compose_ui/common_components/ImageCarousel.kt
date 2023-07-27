package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.pop.Photo


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageCarousel(
    pagerState: PagerState = rememberPagerState(),
    getImages: () -> List<Photo>?,
    onZoomImage: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val images by remember { derivedStateOf { getImages().orEmpty() } }

    val modifier = if (images.isNotEmpty()) Modifier
        .padding(spacing.small)
        .fillMaxWidth()
        .height(300.dp) else Modifier

    val image = if (images.isNotEmpty()) URLConstants.S3_IMAGE_BASE_URL +
            images.elementAtOrNull(pagerState.currentPage)?.fileName.toString() else ""

    Box(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            count = images.size,
            itemSpacing = spacing.small
        ) {

            RemoteImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                imageLink = image,
                error = R.mipmap.img_default_pop,
                contentScale = ContentScale.FillBounds
            )
        }

        if (images.isNotEmpty()) {
            Icon(
                modifier = Modifier
                    .padding(end = spacing.small, bottom = spacing.medium)
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .background(color = Color.Black.copy(alpha = .5f), shape = CircleShape)
                    .padding(spacing.extraSmall)
                    .clip(CircleShape)
                    .clickable {
                        Log.d("ImageCarousel", "current page : ${pagerState.currentPage}")
                        Log.d("ImageCarousel", "image link : $image")
                        onZoomImage(image)
                    },
                painter = painterResource(id = R.drawable.ic_full_screen),
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = "${images.size} / ${pagerState.currentPage + 1}",
                color = Color.White,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = spacing.small, top = spacing.small)
            )
        }
    }
}
