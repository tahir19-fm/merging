package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun HomeTabContent(
    messages: List<Message?>,
    hasFocus: MutableState<Boolean>,
    onLikeClicked: (Int, Message?) -> Unit,
    onCommentClicked: (Message?) -> Unit,
    onReadMoreClicked: (Message?) -> Unit,
    onGoToOtherFarmerProfileClicked: (Message?) -> Unit,
    onSearch: () -> Unit,
    onClose: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val text = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {

                SearchBar(text = text, hasFocus = hasFocus, onSearch = onSearch, onClose = onClose)

                LazyRow(modifier = Modifier.padding(spacing.extraSmall)) {
                    items(messages.distinctBy { it?.createdBy }) { message ->
                        ProfileWithBadge(
                            userName = "${message?.firstName} ${message?.lastName}",
                            imageLink = message?.profileImage ?: "",
                            onGoToOtherFarmerProfileClicked = {
                                onGoToOtherFarmerProfileClicked.invoke(message)
                            }
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
                        onReadMoreClicked = onReadMoreClicked
                    )
                }
            }
        }
    }

    if (messages.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}