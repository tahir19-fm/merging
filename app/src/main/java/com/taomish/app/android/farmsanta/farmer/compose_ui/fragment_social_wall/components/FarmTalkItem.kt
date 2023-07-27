package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ProfileImageWithName
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun FarmTalkItem(
    message: Message,
    index: Int,
    TopActionsRow: @Composable (BoxScope.() -> Unit),
    CenterIcon: @Composable (BoxScope.() -> Unit)? = null,
    onLikeClicked: (Int, Message?) -> Unit,
    onCommentClicked: (Message?) -> Unit,
    onReadMoreClicked: (Message?) -> Unit
) {
    val spacing = LocalSpacing.current
    if (
        message.images != null &&
        message.images?.firstOrNull { it != null && !it.fileName.isNullOrEmpty() } != null
    ) {
        FarmTalkImageItem(
            message = message,
            TopActionsRow = TopActionsRow,
            CenterIcon = CenterIcon,
            onLikeClicked = { onLikeClicked(index, it) },
            onCommentClicked = onCommentClicked,
            onReadMoreClicked = onReadMoreClicked
        )
    } else {
        FarmTalkTextItem(
            message = message,
            TopActionsRow = {
                ProfileImageWithName(
                    modifier = Modifier.padding(start = spacing.small),
                    name = "${message.firstName} ${message.lastName}",
                    profileImageLink = URLConstants.S3_IMAGE_BASE_URL + message.profileImage,
                    textStyle = MaterialTheme.typography.caption
                )
            },
            onLikeClicked = { onLikeClicked(index, it) },
            onCommentClicked = onCommentClicked,
            onReadMoreClicked = onReadMoreClicked
        )
    }
}