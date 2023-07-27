package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_social_message

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialMessageDetailViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.CommentListItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.LikesAndComments
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.ProfileImageWithVerticalName
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.SocialWallCommentsBottomSheetContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_social_message.components.ActionsRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun SocialMessageFragmentScreen(
    viewModel: SocialMessageDetailViewModel = viewModel(),
    message: Message,
    name: String,
    onLikeClicked: (Message?) -> Unit,
    onShareClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val pagerState = rememberPagerState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var showImages by remember {
        mutableStateOf(
            message.images.isNullOrEmpty().not() &&
                    message.images?.firstOrNull { it != null && !it.fileName.isNullOrEmpty() } != null
        )
    }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val onCommentClicked: (Message?) -> Unit = {
        viewModel.selectedMessage.value = it
        scope.launch { sheetState.show() }
    }

    BackHandler(sheetState.isVisible) {
        scope.launch { sheetState.hide() }
        focusManager.clearFocus()
        viewModel.textError.value = false
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SocialWallCommentsBottomSheetContent(
                text = viewModel.comment,
                textError = viewModel.textError,
                onComment = {
                    viewModel.onComment(
                        context = context,
                        userName = name,
                        refresh = {
                            scope.launch { sheetState.hide() }
                            focusManager.clearFocus()
                            viewModel.textError.postValue(false)
                        }
                    )
                },
                comments = viewModel.selectedMessage.value?.comments.orEmpty(),
                likes = viewModel.selectedMessage.value?.likes ?: 0
            )
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {

        Column {
            CommonTopBar(
                activity = context as AppCompatActivity,
                isAddRequired = false,
                title = str(id = R.string.back),
                addClick = {}
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.small, bottom = spacing.small, end = spacing.small)
            ) {
                item {
                    val modifier = if (showImages) Modifier.height(384.dp) else Modifier

                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(spacing.small)
                    ) {

                        if (showImages) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RectangleShape),
                                count = message.images?.size ?: 0,
                                itemSpacing = spacing.large
                            ) { page ->

                                val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                                        message.images[page]
                                            ?.fileName.toString()

                                RemoteImage(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    imageLink = imageLink,
                                    contentScale = ContentScale.FillBounds,
                                    onError = { showImages = false }
                                )
                            }

                            ProfileImageWithVerticalName(
                                firstName = message.firstName ?: "",
                                lastName = message.lastName ?: "",
                                textStyle = MaterialTheme.typography.caption,
                                textColor = Color.White,
                                profileImageLink = "${URLConstants.S3_IMAGE_BASE_URL}${message.profileImage}",
                                imageSize = 32.dp,
                                onGoToOtherFarmerProfileClicked = { }
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = spacing.small, bottom = spacing.medium)
                                    .align(Alignment.BottomStart)
                            ) {
                                Chip(
                                    text = DateUtil().getDateMonthYearFormat(message.createdTimestamp)
                                        ?: "",
                                    textStyle = MaterialTheme.typography.overline,
                                    backgroundColor = Color.LightGray.copy(alpha = .4f)
                                )

                                Column(
                                    modifier = Modifier
                                        .padding(end = spacing.small)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    DotsIndicator(
                                        totalDots = message.images?.size ?: 0,
                                        selectedIndex = pagerState.currentPage
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = DateUtil().getDateMonthYearFormat(message.createdTimestamp)
                                    ?: "",
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = spacing.medium)
                            )

                            ProfileImageWithName(
                                name = "${message.firstName} ${message.lastName}",
                                profileImageLink = URLConstants.S3_IMAGE_BASE_URL + message.profileImage,
                                textStyle = MaterialTheme.typography.body2,
                                imageSize = 32.dp
                            )
                        }
                    }

                    if (showImages) {
                        ActionsRow(
                            message = message,
                            onLikeClicked = { onLikeClicked(message) },
                            onCommentClicked = { onCommentClicked(message) },
                            onShareClicked = onShareClicked,
                            onBookmarkClicked = onBookmarkClicked,
                        )
                    }

                    Text(text = message.title ?: "", style = MaterialTheme.typography.body2)

                    Text(
                        text = HtmlCompat.fromHtml(
                            message.description ?: "",
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        ).toString(),
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Justify,
                    )

                    FlowRow(horizontalGap = spacing.extraSmall, verticalGap = spacing.extraSmall) {
                        (message.tags.orEmpty()).forEach { tag ->
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
                                    text = "#$tag",
                                    color = Color.Blue,
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }
                    }

                    LikesAndComments(
                        likes = message.likes ?: 0,
                        comments = message.comments?.size ?: 0,
                        onLikeClicked = { onLikeClicked(message) },
                        onCommentClicked = { onCommentClicked(message) },
                    )
                }

                items(message.comments.orEmpty()) { comment ->
                    CommentListItem(
                        comment = comment
                    ) { }
                }

            }
        }
    }
}