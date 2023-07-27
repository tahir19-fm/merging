package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_activities.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun LikeItem(message: Message) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val userImage = URLConstants.S3_IMAGE_BASE_URL + message.profileImage

        val imageUrl = URLConstants.S3_IMAGE_BASE_URL +
                message.images?.elementAtOrNull(0)
                ?.fileName.toString()
        RemoteImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp),
            imageLink = userImage,
            contentScale = ContentScale.Crop
        )


        Text(
            text = "${if ((message.selfLike ?: 0) > 0) str(id = R.string.you_and_other) else ""} ${message.likes ?: ""}" +
                    " ${str(id = R.string.people)} ${str(id = R.string.liked_your_post)} ${message.title}",
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth(.8f)
        )


        RemoteImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp),
            imageLink = imageUrl,
            contentScale = ContentScale.Crop,
            error = R.mipmap.img_default_pop
        )
    }
}