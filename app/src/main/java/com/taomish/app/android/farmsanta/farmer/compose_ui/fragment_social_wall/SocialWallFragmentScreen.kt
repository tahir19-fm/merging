package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.farmerTabIndicatorOffset
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialWallViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.ExploreTabContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.HomeTabContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.MyPostsTabContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.SocialWallCommentsBottomSheetContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OutrageousOrange
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun SocialWallFragmentScreen(
    viewModel: SocialWallViewModel,
    trendingTags: List<String>?,
    name: String,
    profileImage: String,
    onRefresh: () -> Unit,
    onGoToDetailClicked: (Message?) -> Unit,
    onAllPostSelected: () -> Unit,
    onMostLikedPostsSelected: () -> Unit,
    onMostCommentedPostSelected: () -> Unit,
    onClickMyActivities: () -> Unit,
    onCreatePostClicked: () -> Unit,
    onGoToOtherFarmerProfileClicked: (Message?) -> Unit,
    onAddClick: () -> Unit,
    onPostLike: (Int, Message?) -> Unit,
    onDeleteClicked: (Message?) -> Unit,
    onTrendingTagSelected: (String) -> Unit = {},
    onSearch: () -> Unit,
    onClose: () -> Unit,
    onShareClicked:(Message?)->Unit={}
) {
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val tabs =
        listOf(str(id = R.string.explore), str(id = R.string.home), str(id = R.string.my_posts))
    val state = rememberPagerState()
    var selected by remember { mutableStateOf(0) }
    var size by remember { mutableStateOf(Size.Zero) }
    val width: @Composable () -> Dp = { with(LocalDensity.current) { size.width.toDp() } }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    selected = state.currentPage
    val onCommentClicked: (Message?) -> Unit = {
        viewModel.selectedMessage.value = it
        scope.launch { sheetState.show() }
    }

    val onSearchPop: () -> Unit = {
        if (viewModel.searchText.value.length > 2) {
            focusManager.clearFocus()
            onSearch()
        } else {
            context.showToast(R.string.search_crops_msg)
        }
    }

    BackHandler(sheetState.isVisible || hasFocus.value || viewModel.searchText.value.isNotEmpty()) {
        scope.launch { sheetState.hide() }
        focusManager.clearFocus()
        viewModel.textError.value = false
        if (viewModel.searchText.value.isNotEmpty()) {
            viewModel.searchText.postValue("")
            onClose()
        }
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
                            onRefresh()
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
        Column(modifier = Modifier.fillMaxSize()) {
            CommonTopBar(
                title = stringResource(id = R.string.farm_talk),
                activity = context as AppCompatActivity,
                addClick = onAddClick
            )

            TabRow(
                selectedTabIndex = selected,
                backgroundColor = Color.Transparent,
                indicator = {
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .farmerTabIndicatorOffset(width(), it[selected]),
                        color = Color.OutrageousOrange,
                        height = 3.dp
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selected == index,
                        onClick = {
                            selected = index
                            scope.launch { state.scrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = tab,
                                style = MaterialTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .onGloballyPositioned { size = it.size.toSize() }
                            )
                        },
                        selectedContentColor = Color.OutrageousOrange,
                        unselectedContentColor = Color.Black
                    )
                }
            }

            HorizontalPager(
                state = state,
                count = tabs.size
            ) { page ->
                when (page) {
                    0 -> {
                        ExploreTabContent(
                            messages = viewModel.socialMessages,
                            searchText = viewModel.searchText,
                            trendingTags = trendingTags,
                            hasFocus = hasFocus,
                            onLikeClicked = onPostLike,
                            onCommentClicked = onCommentClicked,
                            onReadMoreClicked = onGoToDetailClicked,
                            onAllPostSelected = onAllPostSelected,
                            onMostLikedPostsSelected = onMostLikedPostsSelected,
                            onMostCommentedPostSelected = onMostCommentedPostSelected,
                            onGoToOtherFarmerProfileClicked = onGoToOtherFarmerProfileClicked,
                            onTrendingTagSelected = onTrendingTagSelected,
                            onSearch = onSearchPop,
                            onClose = onClose,
                            onShareClicked = onShareClicked
                        )
                    }
                    1 -> {
                        HomeTabContent(
                            messages = viewModel.socialMessages,
                            searchText=viewModel.searchText,
                            hasFocus = hasFocus,
                            onLikeClicked = onPostLike,
                            onCommentClicked = onCommentClicked,
                            onReadMoreClicked = onGoToDetailClicked,
                            onGoToOtherFarmerProfileClicked = onGoToOtherFarmerProfileClicked,
                            onSearch = onSearchPop,
                            onClose = onClose,
                            onShareClicked = onShareClicked
                        )
                    }
                    2 -> {
                        MyPostsTabContent(
                            myPosts = viewModel.mySocialMessages,
                            name = name,
                            profileImage = profileImage,
                            onClickMyActivities = onClickMyActivities,
                            onDeleteClicked = onDeleteClicked,
                            onLikeClicked = onPostLike,
                            onCommentClicked = onCommentClicked,
                            onGoToDetailsClicked = onGoToDetailClicked,
                            onReadMoreClicked = onGoToDetailClicked,
                            onCreatePostClicked = onCreatePostClicked,
                            onShareClicked = onShareClicked
                        )
                    }
                }
            }
        }
    }

}
