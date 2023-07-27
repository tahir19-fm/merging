package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OutrageousOrange
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun ExploreTabContent(
    messages: List<Message?>,
    searchText: MutableState<String>,
    trendingTags: List<String>?,
    hasFocus: MutableState<Boolean>,
    onLikeClicked: (Int, Message?) -> Unit,
    onCommentClicked: (Message?) -> Unit,
    onReadMoreClicked: (Message?) -> Unit,
    onAllPostSelected: () -> Unit,
    onMostLikedPostsSelected: () -> Unit,
    onMostCommentedPostSelected: () -> Unit,
    onGoToOtherFarmerProfileClicked: (Message?) -> Unit,
    onTrendingTagSelected: (String) -> Unit = {},
    onSearch: () -> Unit,
    onClose: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val expanded = remember { mutableStateOf(false) }
    val options = listOf(
        str(id = R.string.all_posts),
        str(id = R.string.most_liked_posts),
        str(id = R.string.most_commented_posts)
    )
    val selected = remember { mutableStateOf(options[0]) }
    val onSelectOption: (String) -> Unit = {
        when (it) {
            options[0] -> onAllPostSelected()
            options[1] -> onMostLikedPostsSelected()
            options[2] -> onMostCommentedPostSelected()
        }
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            SearchBar(
                text = searchText,
                hasFocus = hasFocus,
                onSearch = onSearch,
                onClose = onClose
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TrendRow(
                    modifier = Modifier.fillMaxWidth(.9f),
                    trending = trendingTags,
                    onTrendSelected = {
                        onTrendingTagSelected(it)
                    }
                )

                Column(modifier = Modifier.padding(end = spacing.small)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings_filter),
                        contentDescription = null,
                        tint = Color.Cameron,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { expanded.postValue(true) }
                    )

                    FarmerDropDownContent(
                        modifier = Modifier.wrapContentWidth(),
                        expanded = expanded,
                        selected = selected,
                        items = options,
                        onSelectOption = { _, option -> onSelectOption(option) },
                        selectedItemColor = Color.OutrageousOrange
                    )
                }
            }
        }

        itemsIndexed(messages) { index, message ->
            if (message != null) {
                FarmTalkItem(
                    message = message,
                    index = index,
                    TopActionsRow = {
                        ProfileImageWithVerticalName(
                            firstName = message.firstName,
                            lastName = message.lastName,
                            textStyle = MaterialTheme.typography.caption,
                            textColor = Color.White,
                            profileImageLink = "${URLConstants.S3_IMAGE_BASE_URL}${message.profileImage}",
                            imageSize = 32.dp,
                            onGoToOtherFarmerProfileClicked = {
                                onGoToOtherFarmerProfileClicked(message)
                            }
                        )
                    },
                    onLikeClicked = onLikeClicked,
                    onCommentClicked = onCommentClicked,
                    onReadMoreClicked = {
                        onReadMoreClicked.invoke(message)
                    }
                )
            }
        }
    }

    if (messages.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}