package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_social_message.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Valencia
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun ActionsRow(
    message: Message?,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val isLiked = (message?.selfLike ?: 0) > 0
            Icon(
                painter = painterResource(id = if (isLiked) R.drawable.ic_filled_heart else R.drawable.ic_heart),
                contentDescription = null,
                tint = if (isLiked) Color.Valencia else Color.Gray,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(24.dp)
                    .clickable { onLikeClicked() }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_chat),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .clip(CircleShape)
                    .size(24.dp)
                    .clickable { onCommentClicked() }
            )

        }

        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            tint = if (message?.bookMarked == true) Color.Cameron else Color.Black,
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onBookmarkClicked() }
        )
    }
}