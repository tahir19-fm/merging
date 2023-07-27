package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_activities.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment
import com.taomish.app.android.farmsanta.farmer.models.api.message.Image
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import java.text.ParseException


@Composable
fun CommentItem(comment: Comment, postTitle: String, postImage: Image?) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RemoteImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp),
            imageLink = "userImageUrl",
            contentScale = ContentScale.Crop
        )

        var date = ""
        if (DateUtil().isPostedWithin24hours(comment.createdTimestamp)) {
            date = DateUtil().getDateMonthYearFormat(comment.createdTimestamp)
        } else {
            try {
                date = DateUtil().getDifferenceHours(comment.createdTimestamp)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(.8f)
        ) {
            Text(text = "${comment.firstName} ${str(id = R.string.comments_on_your_post)}: ", style = MaterialTheme.typography.caption)
            Text(
                text = "$postTitle $date",
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )
        }

        val messageImageUrl = URLConstants.S3_IMAGE_BASE_URL +
                postImage?.fileName.toString()


        RemoteImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp),
            imageLink = messageImageUrl,
            contentScale = ContentScale.Crop,
            error = R.mipmap.img_default_pop
        )
    }
}