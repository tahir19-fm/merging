package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FarmTalkImageItem(
    message: Message?,
    CenterIcon: @Composable (BoxScope.() -> Unit)? = null,
    TopActionsRow: @Composable (BoxScope.() -> Unit),
    onLikeClicked: (Message?) -> Unit,
    onCommentClicked: (Message?) -> Unit,
    onReadMoreClicked: (Message?) -> Unit,
    onShareClicked: (Message?) -> Unit = {},

    ) {
    val spacing = LocalSpacing.current
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .clickable(indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { onReadMoreClicked(message) })
    ) {
        Box(
            modifier = Modifier
                .height(384.dp)
                .fillMaxWidth()
        ) {


            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape),
                count = message?.images?.size ?: 0,
                itemSpacing = spacing.large
            ) { page ->
                val imageLink =
                    URLConstants.S3_IMAGE_BASE_URL + message?.images?.getOrNull(page)?.fileName.toString()

                RemoteImage(
                    modifier = Modifier.fillMaxSize(),
                    imageLink = imageLink,
                    error = R.mipmap.img_default_pop,
                    contentScale = ContentScale.FillBounds
                )
            }

            ActionsColumn(message = message,
                onFavoriteClicked = { onLikeClicked(message) },
                onShareClicked = {onShareClicked.invoke(message)},
                onCommentClicked = { onCommentClicked(message) })

            CenterIcon?.invoke(this)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = spacing.small, end = spacing.small, bottom = spacing.medium
                    )
                    .align(Alignment.BottomStart)
            ) {
                Chip(
                    text = DateUtil().getDateMonthYearFormat(message?.createdTimestamp),
                    textStyle = MaterialTheme.typography.overline,
                    backgroundColor = Color.Black.copy(alpha = .4f)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
                ) {
                    DotsIndicator(
                        totalDots = message?.images?.size ?: 0,
                        selectedIndex = pagerState.currentPage
                    )
                }
            }

            TopActionsRow()
        }
        LikesAndComments(likes = message?.likes ?: 0,
            comments = message?.comments?.size ?: 0,
            onLikeClicked = { onLikeClicked(message) },
            onCommentClicked = { onCommentClicked(message) },
            onShareClicked = {onShareClicked(message)})

        Text(text = message?.title ?: "", style = MaterialTheme.typography.body2)

        Text(
            text = HtmlCompat.fromHtml(
                message?.description ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString(),
            style = MaterialTheme.typography.caption,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth(),
        )

        ReadMoreText(
            onReadMoreClicked = { onReadMoreClicked.invoke(message) }, isExpandedable = false
        )

        if (message?.agronomyManager == true) Agronomist()

        FlowRow(horizontalGap = spacing.extraSmall, verticalGap = spacing.extraSmall) {
            (message?.tags.orEmpty()).forEach { tag ->
                if (tag.contains(" ")) {
                    tag.split(" ").forEach { s ->
                        Text(
                            text = "#$s",
                            color = Color.Blue,
                            style = MaterialTheme.typography.caption
                        )
                    }
                } else {
                    Text(
                        text = "#$tag", color = Color.Blue, style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}