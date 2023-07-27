package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.Task
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@Composable
fun CroppingStageIcon(
    task: Task,
    backgroundColor: Color
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val imageUrl = URLConstants.S3_IMAGE_BASE_URL + task.taskImages?.elementAtOrNull(0)

        RemoteImage(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            imageLink = imageUrl,
            contentScale = ContentScale.Crop,
            error = R.drawable.ic_seed_selection
        )

        val words = task.oprationName.notNull().split(" ").let {
            if (it.size > 2) it.take(2) else it
        }.joinToString("\n")

        Text(
            text = words,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.overline,
            modifier = Modifier
                .padding(top = spacing.extraSmall)
                .background(color = backgroundColor, shape = shape)
                .padding(spacing.extraSmall)
        )
    }
}