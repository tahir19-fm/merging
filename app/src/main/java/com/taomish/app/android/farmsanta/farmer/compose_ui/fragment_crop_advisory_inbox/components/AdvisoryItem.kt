package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil.getUUIDName


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdvisoryItem(
    advisory: CropAdvisory,
    cropName: String?,
    advisoryTag: String?,
    onAdvisoryClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onAdvisoryClicked
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImageWithName(
                name = "${advisory.firstName} ${advisory.lastName}",
                profileImageLink = URLConstants.S3_IMAGE_BASE_URL + advisory.profileImage,
                textStyle = MaterialTheme.typography.body2
            )

            Text(
                text = DateUtil().getDateMonthYearFormat(advisory.createdTimestamp),
                style = MaterialTheme.typography.caption
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = spacing.small)
                    .clip(RectangleShape)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = onAdvisoryClicked
                    ),
                state = pagerState,
                count = advisory.photos?.size ?: 1 // to show default image
            ) { page ->
                val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                        advisory.photos
                            ?.elementAtOrNull(page)
                            ?.fileName.toString()

                RemoteImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape),
                    imageLink = imageLink,
                    contentScale = ContentScale.FillBounds,
                    error = R.mipmap.img_default_pop
                )
            }

            ChipIcon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = spacing.small, bottom = spacing.small),
                iconId = R.drawable.ic_forword_arrow_round,
                backgroundColor = Color.White,
                iconColor = Color.Unspecified,
                size = 40.dp,
                onClick = onAdvisoryClicked
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = spacing.small, top = spacing.small)
            ) {
                Chip(
                    modifier = Modifier.padding(top = spacing.extraSmall),
                    text = "${str(id = R.string.plant)}: ${cropName ?: ""}",
                    textStyle = MaterialTheme.typography.overline,
                    backgroundColor = Color.Black.copy(alpha = .4f)
                )

                if (!advisory.photos?.firstOrNull()?.caption.isNullOrEmpty()) {
                    Chip(
                        modifier = Modifier.padding(top = spacing.small),
                        text = "${str(id = R.string.caption)}: ${advisory.photos?.firstOrNull()?.caption}",
                        textStyle = MaterialTheme.typography.overline,
                        backgroundColor = Color.Black.copy(alpha = .4f)
                    )
                }
            }

            Chip(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = spacing.medium, start = spacing.small),
                text = "${str(id = R.string.growth_stage)}: ${
                    advisory.growthStage?.let { getUUIDName(it) }
                }",
                textStyle = MaterialTheme.typography.overline,
                backgroundColor = Color.Black.copy(alpha = .4f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DotsIndicator(
                totalDots = advisory.photos?.size ?: 0,
                selectedIndex = pagerState.currentPage,
                unSelectedColor = Color.Gray
            )
        }


        Text(
            text = "${str(id = R.string.advisory)} : ${advisoryTag ?: "N/A"}",
        )

        Text(
            text = advisory.advisory.ifEmpty { str(id = R.string.description_not_available) },
            style = MaterialTheme.typography.caption,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        ReadMoreText(
            onReadMoreClicked = onAdvisoryClicked,
            isExpandedable = false
        )
    }
}