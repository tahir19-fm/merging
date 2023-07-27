package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun LikesAndComments(
    @DrawableRes likeIcon: Int = R.drawable.ic_heart,
    @DrawableRes commentIcon: Int = R.drawable.ic_chat,
    @DrawableRes shareIcon:Int=R.drawable.ic_share,
    likes: Int,
    comments: Int,
    onCommentClicked: () -> Unit = {},
    onLikeClicked: () -> Unit = {},
    onShareClicked:()->Unit={}
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(spacing.extraSmall)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(start = spacing.small)
                .clip(CircleShape)
                .clickable(onClick = onLikeClicked)
                .padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = likeIcon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "$likes ${str(id = R.string.liked)}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = spacing.extraSmall)
            )
        }

        Row(
            modifier = Modifier
                .padding(start = spacing.medium)
                .clip(CircleShape)
                .clickable(onClick = onCommentClicked)
                .padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = commentIcon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "$comments ${str(id = R.string.comments)}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = spacing.extraSmall)
            )
        }
        Row(
            modifier = Modifier
                .padding(start = spacing.medium)
                .clip(CircleShape)
                .clickable(onClick = onShareClicked)
                .padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = shareIcon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = str(id = R.string.share),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = spacing.extraSmall)
            )
        }
    }
}