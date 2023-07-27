package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@Composable
fun TrendingTalkItem(message: Message, onViewTalkClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .size(180.dp, 312.dp)
            .padding(spacing.small),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.extraSmall, vertical = spacing.small)
            ) {

                val imagePath = URLConstants.S3_IMAGE_BASE_URL +
                        message.images?.elementAtOrNull(0)
                            ?.fileName.toString()

                RemoteImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    imageLink = imagePath,
                    error = R.mipmap.img_default_pop,
                    contentScale = ContentScale.FillBounds
                )

                Chip(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = spacing.small, bottom = spacing.small),
                    text = DateUtil().getDateMonthYearFormat(message.createdTimestamp),
                    textPadding = spacing.tiny,
                    textStyle = MaterialTheme.typography.overline,
                    backgroundColor = Color.Black.copy(alpha = .3f)
                )
            }

            ProfileImageWithName(
                name = message.firstName ?: "",
                profileImageLink = URLConstants.S3_IMAGE_BASE_URL + message.profileImage,
                textStyle = MaterialTheme.typography.caption,
                textColor = Color.LightGray,
                imageSize = 24.dp
            )

            Text(
                text = message.description.ifEmpty { str(id = R.string.description_not_available) },
                color = Color.Gray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption
            )

            Text(
                text = str(id = R.string.experts),
                color = Color.LightGray,
                maxLines = 3,
                style = MaterialTheme.typography.caption
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing.extraSmall, top = spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundedShapeButton(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(end = spacing.small),
                text = str(id = R.string.view_talk),
                textPadding = spacing.tiny,
                textStyle = MaterialTheme.typography.caption,
                onClick = onViewTalkClicked
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .background(color = Color.Cameron, shape = CircleShape)
                    .padding(spacing.extraSmall)
            )
        }
    }
}