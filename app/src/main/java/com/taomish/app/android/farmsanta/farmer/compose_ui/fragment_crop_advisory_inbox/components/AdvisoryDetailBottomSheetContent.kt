package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil.getAdvisoryTagName
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil.getUUIDName


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdvisoryDetailBottomSheetContent(advisory: CropAdvisory?) {
    if (advisory == null) {
        Box(modifier = Modifier.padding(1.dp))
        return
    }
    val spacing = LocalSpacing.current
    val pagerState = rememberPagerState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.medium, horizontal = spacing.small)
    ) {
        item {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = spacing.small)
                        .clip(RectangleShape),
                    state = pagerState,
                    count = advisory.photos?.size ?: 1
                ) { page ->
                    val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                            advisory.photos
                                ?.elementAtOrNull(page)
                                ?.fileName.toString()

                    RemoteImage(
                        modifier = Modifier.fillMaxSize(),
                        imageLink = imageLink,
                        contentScale = ContentScale.FillBounds,
                        error = R.mipmap.img_default_pop
                    )
                }


                ProfileImageWithName(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = spacing.small, start = spacing.small),
                    name = "${advisory.firstName} ${advisory.lastName}",
                    profileImageLink = URLConstants.S3_IMAGE_BASE_URL /* + advisory.profileImage */,
                    imageSize = 32.dp,
                    textStyle = MaterialTheme.typography.caption,
                    textColor = Color.White
                )

                /*ChipIcon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = spacing.small, start = spacing.small),
                    iconId = R.drawable.ic_heart,
                    iconColor = Color.White,
                    size = 24.dp,
                    backgroundShape = CircleShape,
                    backgroundColor = Color.LightGray.copy(alpha = .4f),
                    onClick = { onLikeClicked() }
                )*/


                /*Chip(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = spacing.small, bottom = spacing.medium),
                    text = "${stringResource(id = R.string.heart)} 123 Likes",
                    textStyle = MaterialTheme.typography.caption,
                    backgroundColor = Color.White.copy(alpha = .4f)
                )*/
            }


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                DotsIndicator(
                    totalDots = advisory.photos?.size ?: 0,
                    selectedIndex = pagerState.currentPage,
                    unSelectedColor = Color.Gray
                )
            }


            Text(
                text = "${str(id = R.string.advisory)}: ${getAdvisoryTagName(
                    advisory.advisoryTag ?: "",
                    advisory.advisoryTagName ?: ""
                )}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(spacing.small)
            )

            Row(
                modifier = Modifier
                    .padding(vertical = spacing.small)
            ) {
                Chip(
                    text = "${str(id = R.string.plant)}: ${NamesAndFormatsUtil.getCropName(advisory.crop)}",
                    textStyle = MaterialTheme.typography.overline,
                    backgroundColor = Color.Cameron
                )

                Chip(
                    modifier = Modifier.padding(start = spacing.small),
                    text = "${str(id = R.string.growth_stage)}: ${advisory.growthStage?.let { getUUIDName(it) }}",
                    textStyle = MaterialTheme.typography.overline,
                    backgroundColor = Color.Cameron
                )
            }


            Text(
                text = advisory.advisory.ifEmpty { str(id = R.string.description_not_available) },
                style = MaterialTheme.typography.body2
            )

            Text(
                text = "${str(id = R.string.uploaded_on)} : ${DateUtil().getDateMonthYearFormat(advisory.createdTimestamp)}",
                color = Color.Cameron,
                style = MaterialTheme.typography.caption
            )
        }
    }
}