package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants


@Composable
fun ChatMessageReply(imageUrl: String, message: String, dateTime: String) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.medium, horizontal = spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dateTime,
            color = Color.LightGray,
            style = MaterialTheme.typography.overline,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(.2f)
        )

        Text(
            text = message,
            color = Color.Gray,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth(.8f)
                .background(
                    color = Color.Cameron.copy(alpha = .1f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(spacing.small)
        )

        RemoteImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            imageLink = URLConstants.S3_IMAGE_BASE_URL + imageUrl,
            contentScale = ContentScale.FillBounds
        )
    }
}