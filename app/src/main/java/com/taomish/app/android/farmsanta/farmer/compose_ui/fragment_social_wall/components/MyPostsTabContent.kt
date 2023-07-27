package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ChipIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.enums.VIEW
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun MyPostsTabContent(
    myPosts: List<Message?>,
    name: String,
    profileImage: String,
    onClickMyActivities: () -> Unit,
    onDeleteClicked: (Message?) -> Unit,
    onLikeClicked: (Int, Message?) -> Unit,
    onCommentClicked: (Message?) -> Unit,
    onGoToDetailsClicked: (Message) -> Unit,
    onReadMoreClicked: (Message?) -> Unit,
    onShareClicked:(Message?)->Unit={},
    onCreatePostClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val chuckedList = myPosts.chunked(3)
    val view = remember { mutableStateOf(VIEW.LIST) }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            MyPostsProfileCard(
                userName = name,
                imageLink = profileImage,
                myPostsCount = myPosts.size,
                onClickMyActivities = onClickMyActivities,
                onCreatePostClicked = onCreatePostClicked
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.extraLarge),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_list_view),
                    contentDescription = null,
                    tint = if (view.value == VIEW.LIST) Color.Cameron else Color.LightGray,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { view.value = VIEW.LIST }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_tile_view),
                    contentDescription = null,
                    tint = if (view.value == VIEW.GRID) Color.Cameron else Color.LightGray,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { view.value = VIEW.GRID }
                )
            }
        }

        if (view.value == VIEW.LIST) {
            itemsIndexed(myPosts) { index, message ->
                if (message != null)
                    FarmTalkItem(
                        message = message,
                        index = index,
                        CenterIcon = {
                            ChipIcon(
                                modifier = Modifier.align(Alignment.Center),
                                iconId = R.drawable.ic_forword_arrow_round,
                                backgroundColor = Color.White,
                                iconColor = Color.Unspecified,
                                size = 56.dp,
                                onClick = {
                                    onGoToDetailsClicked(message)
                                }
                            )
                        },
                        TopActionsRow = {
                            ActionIcon(
                                text = str(id = R.string.delete),
                                imageVector = Icons.Filled.Delete,
                                color = Color.Red,
                                onClick = { onDeleteClicked(message) }
                            )
                        },
                        onLikeClicked = onLikeClicked,
                        onCommentClicked = onCommentClicked,
                        onReadMoreClicked = onReadMoreClicked,
                        onShareClicked = onShareClicked
                    )
            }
        } else {
            items(chuckedList) { chunk ->
                GridViewRow(messages = chunk, onMessageClick = onGoToDetailsClicked)
            }
        }
    }

    if (myPosts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}

