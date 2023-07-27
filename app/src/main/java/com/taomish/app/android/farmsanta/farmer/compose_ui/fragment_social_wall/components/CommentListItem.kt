package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ProfileImageWithName
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Thunderbird
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import java.text.ParseException


@Composable
fun CommentListItem(
    comment: Comment,
    onMoreOptionClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    var date = comment.createdTimestamp
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(.8f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImageWithName(
                    name = "${comment.firstName} ${comment.lastName}",
                    textStyle = MaterialTheme.typography.caption,
                    textColor = Color.Gray,
                    profileImageLink = URLConstants.S3_IMAGE_BASE_URL + comment.profileImage ,
                    imageSize = 32.dp
                )


                if (DateUtil().isPostedWithin24hours(comment.createdTimestamp)) {
                    date = DateUtil().getDateMonthYearFormat(comment.createdTimestamp)
                } else {
                    try {
                        date = DateUtil().getDifferenceHours(comment.createdTimestamp)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }

                Text(
                    text = date,
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }

            Text(
                text = comment.comment,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                modifier = Modifier.padding(start = spacing.small)
            )

           /* LikedAndComments(
                likes = likes,
                comments = comments,
                onReplyClicked = onReplyClicked
            )*/

            Divider(
                modifier = Modifier.padding(vertical = spacing.small),
                thickness = 0.3.dp
            )
        }

        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .padding(spacing.extraSmall)
                .size(24.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .clickable { onMoreOptionClicked() }
        )

    }
}

@Composable
fun LikedAndComments(
    likes: Int,
    comments: Int,
    onReplyClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(top = spacing.small)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filled_heart),
                contentDescription = null,
                tint = Color.Thunderbird,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .background(color = Color.Thunderbird.copy(alpha = .1f), shape = CircleShape)
                    .border(width = 1.dp, color = Color.Thunderbird, shape = CircleShape)
                    .padding(spacing.extraSmall)
                    .size(20.dp)
            )

            Text(
                text = "$likes ${str(id = R.string.liked)}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier.padding(start = spacing.small)
            )

            BadgedBox(
                modifier = Modifier.padding(start = spacing.small),
                badge = {
                    Badge(backgroundColor = Color.Cameron) {
                        Text(
                            text = "$comments",
                            style = MaterialTheme.typography.overline,
                            color = Color.White
                        )
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .background(color = Color.LightGray.copy(alpha = .6f), shape = CircleShape)
                        .padding(spacing.extraSmall)
                        .size(20.dp)
                )
            }

            Text(
                text = str(id = R.string.reply),
                style = MaterialTheme.typography.caption,
                color = Color.Cameron,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = onReplyClicked
                    )
            )
        }
    }
}