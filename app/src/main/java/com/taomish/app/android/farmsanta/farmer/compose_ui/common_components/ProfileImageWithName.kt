package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ProfileImageWithName(
    modifier: Modifier = Modifier,
    name: String,
    textColor: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Bold,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    profileImageLink: String,
    imageSize: Dp = 48.dp,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RemoteImage(
            modifier = Modifier
                .padding(vertical = spacing.small)
                .size(imageSize)
                .clip(CircleShape),
            imageLink = profileImageLink,
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = name,
            fontWeight = fontWeight,
            modifier = Modifier.padding(horizontal = spacing.extraSmall),
            color = textColor,
            style = textStyle,
            overflow = TextOverflow.Ellipsis
        )
    }
}