package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@Composable
fun ExtraTask(
    modifier: Modifier = Modifier,
    title: String?,
    imageUrl: String,
    description: String?,
    backgroundColor: Color
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Column(modifier = modifier.height(200.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f)
        ) {
            RemoteImage(
                modifier = Modifier.fillMaxSize(),
                imageLink = imageUrl,
                contentScale = ContentScale.FillBounds,
                error = R.drawable.ic_sowing_methods
            )

            Text(
                text = title.notNull(),
                color = Color.Cameron,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = spacing.small, start = spacing.small, end = spacing.small)
                    .align(Alignment.TopStart)
                    .background(color = Color.RiceFlower, shape = shape)
                    .padding(vertical = spacing.extraSmall, horizontal = spacing.small)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(backgroundColor),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Text(
                text = description.notNull(),
                color = Color.White,
                style = MaterialTheme.typography.caption,
                maxLines = 3,
                overflow = TextOverflow.Visible,
                modifier = Modifier.padding(horizontal = spacing.extraSmall)
            )

            Text(
                text = str(id = R.string.read_more),
                color = Color.RiceFlower,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(horizontal = spacing.extraSmall)
            )
        }
    }
}