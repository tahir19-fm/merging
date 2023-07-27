package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ChipIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Valencia
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun BoxScope.ActionsColumn(
    message: Message?,
    onFavoriteClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: ()->Unit={}
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = spacing.small)
    ) {

        val isLiked = (message?.selfLike ?: 0) > 0
        ChipIcon(
            iconId = if (isLiked) R.drawable.ic_filled_heart else R.drawable.ic_heart,
            iconColor = if (isLiked) Color.Valencia else Color.White,
            size = 24.dp,
            backgroundShape = CircleShape,
            backgroundColor = if (isLiked) Color.LightGray.copy(.4F) else
                Color.Black.copy(alpha = .7f),
            onClick = { onFavoriteClicked() }
        )

        ChipIcon(
            iconId = R.drawable.ic_chat,
            iconColor = Color.White,
            size = 24.dp,
            backgroundShape = CircleShape,
            backgroundColor = Color.Black.copy(alpha = .7f),
            onClick = { onCommentClicked() }
        )

        ChipIcon(
            iconId = R.drawable.ic_share,
            iconColor = Color.White,
            size = 24.dp,
            backgroundShape = CircleShape,
            backgroundColor = Color.Black.copy(alpha = .7f),
            onClick = { onShareClicked() }
        )
    }
}