package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_other_farmer_profile

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ChipIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.enums.VIEW
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_other_farmer_profile.components.OtherFarmerProfileCard
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.FarmTalkItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.GridViewRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun OtherFarmerProfileFragmentScreen(
    farmer: Message?,
    posts: List<Message?>,
    onLikeClicked: (Message?) -> Unit,
    onCommentClicked: (Message?) -> Unit,
    onReadMoreClicked: () -> Unit,
    onGoToDetailsClicked: (Message?) -> Unit
) {
    val spacing = LocalSpacing.current
    val chuckedList = posts.chunked(3)
    val view = remember { mutableStateOf(VIEW.LIST) }
    val context = LocalContext.current
    val userName = "${farmer?.firstName} ${farmer?.lastName}"

    Column {
        CommonTopBar(
            activity = context as AppCompatActivity,
            isAddRequired = false,
            title = userName,
            addClick = {}
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {

                OtherFarmerProfileCard(
                    userName = userName,
                    imageLink = farmer?.profileImage ?: "",
                    myPostsCount = posts.size
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

            if (posts.isNotEmpty()) {
                if (view.value == VIEW.LIST) {
                    itemsIndexed(posts) { index, message ->
                        message?.let {
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
                                        onClick = { onGoToDetailsClicked(message) }
                                    )
                                },
                                TopActionsRow = { },
                                onLikeClicked = { _, message -> onLikeClicked(message) },
                                onCommentClicked = onCommentClicked,
                                onReadMoreClicked = { onReadMoreClicked.invoke() }
                            )
                        }
                    }
                } else {
                    items(chuckedList) { chunk ->
                        GridViewRow(messages = chunk, onMessageClick = onGoToDetailsClicked)
                    }
                }
            } else {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        EmptyList(text = str(id = R.string.nothing_to_show))
                    }
                }
            }
        }

    }

}
