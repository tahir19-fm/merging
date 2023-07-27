package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.R


@Composable
fun ProfileWithBadge(
    userName: String,
    imageLink: String,
    showBadge: Boolean = true,
    onGoToOtherFarmerProfileClicked: () -> Unit,
    @DrawableRes error: Int = R.mipmap.ic_avatar
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onGoToOtherFarmerProfileClicked
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            RemoteImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                imageLink = URLConstants.S3_IMAGE_BASE_URL + imageLink,
                contentScale = ContentScale.FillBounds,
                error = error
            )

            if (showBadge) {
                Row(

                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = spacing.tiny, bottom = spacing.tiny)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Blue)
                    )
                }
            }
        }

        Text(
            text = userName,
            style = MaterialTheme.typography.caption
        )
    }

}