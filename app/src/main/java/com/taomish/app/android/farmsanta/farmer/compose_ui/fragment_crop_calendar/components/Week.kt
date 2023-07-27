package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.StageWeek
import com.taomish.app.android.farmsanta.farmer.utils.notNull
import kotlin.math.abs


@Composable
fun Week(
    modifier: Modifier = Modifier,
    week: StageWeek,
    backgroundColor: Color,
    onWeekClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.medium, vertical = spacing.small)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onWeekClick
            )
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val weekNo = week.weekInfo?.let {
                if (it == 0) str(id = R.string.current_week)
                else if (it < 0) "${abs(it)} ${str(id = R.string.week)} ${str(id = R.string.before)}"
                else "${str(id = R.string.week)} $it"
            } ?: ""
            Text(
                text = weekNo,
                color = backgroundColor,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )


            if (week.isCurrentTask) {
                StageTag(tag = str(id = R.string.current_task), backgroundColor = backgroundColor)
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor, shape = RectangleShape),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageUrl = URLConstants.S3_IMAGE_BASE_URL +
                    week.tasklist?.firstOrNull()?.taskImages
                        ?.elementAtOrNull(0)

            RemoteImage(
                modifier = Modifier
                    .padding(spacing.small)
                    .size(120.dp),
                imageLink = imageUrl,
                error = R.drawable.ic_crop_planning
            )

            Column(
                modifier = Modifier.padding(spacing.small),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                Text(
                    text = week.tasklist?.firstOrNull()?.oprationType.notNull(),
                    color = Color.Cameron,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .background(color = Color.RiceFlower, shape = shape)
                        .padding(vertical = spacing.small, horizontal = spacing.medium)
                )

                Text(
                    text = week.tasklist?.firstOrNull()?.oprationDescription.notNull(),
                    color = Color.White,
                    style = MaterialTheme.typography.caption,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = str(id = R.string.read_more),
                    color = Color.RiceFlower,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(bottom = spacing.extraSmall)
                )
            }
        }
//
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = spacing.small)
//        ) {
//            if ((week.tasklist?.size ?: 0) > 1) {
//                val imageUrl = URLConstants.S3_IMAGE_BASE_URL +
//                        week.tasklist?.get(1)?.taskImages
//                            ?.elementAtOrNull(0)
//                ExtraTask(
//                    modifier = Modifier
//                        .padding(end = spacing.extraSmall)
//                        .fillMaxWidth(.5f),
//                    title = week.tasklist?.get(1)?.oprationType,
//                    imageUrl = imageUrl,
//                    description = week.tasklist?.get(1)?.oprationDescription,
//                    backgroundColor = backgroundColor
//                )
//            }
//
//            if ((week.tasklist?.size ?: 0) > 2) {
//                val imageUrl = URLConstants.S3_IMAGE_BASE_URL +
//                        week.tasklist?.get(2)?.taskImages
//                            ?.elementAtOrNull(0)
//                ExtraTask(
//                    modifier = Modifier
//                        .padding(start = spacing.extraSmall)
//                        .fillMaxWidth(),
//                    title = week.tasklist?.get(2)?.oprationType,
//                    imageUrl = imageUrl,
//                    description = week.tasklist?.get(2)?.oprationDescription,
//                    backgroundColor = backgroundColor
//                )
//            }
//        }
    }
}