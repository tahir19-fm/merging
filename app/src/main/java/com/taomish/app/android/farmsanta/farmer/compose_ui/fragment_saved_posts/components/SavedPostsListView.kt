package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_posts.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.FarmTalkItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.ProfileImageWithVerticalName
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun SavedPostsListView(myPosts: List<Message>) {
    LazyColumn {
        itemsIndexed(myPosts) { index, message ->
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
                        onGoToOtherFarmerProfileClicked = { }
                    )
                },
                onLikeClicked = { _, _ -> },
                onCommentClicked = { }
            ) { }
        }
    }
}