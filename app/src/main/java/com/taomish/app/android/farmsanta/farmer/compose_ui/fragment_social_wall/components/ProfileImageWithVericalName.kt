package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing

@Composable
fun ProfileImageWithVerticalName(
    modifier: Modifier = Modifier,
    firstName: String?,
    lastName: String?,
    textColor: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Bold,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    profileImageLink: String,
    imageSize: Dp = 48.dp,
    onGoToOtherFarmerProfileClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .padding(spacing.extraSmall)
            .background(color = Color.Black.copy(alpha = .4f), shape = CircleShape)
            .padding(spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RemoteImage(
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = onGoToOtherFarmerProfileClicked
                ),
            imageLink = profileImageLink,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.padding(horizontal = spacing.extraSmall),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${firstName ?: ""} ",
                fontWeight = fontWeight,
                modifier = Modifier,
                color = textColor,
                style = textStyle
            )

            Text(
                text = lastName ?: "",
                fontWeight = fontWeight,
                modifier = Modifier,
                color = textColor,
                style = textStyle
            )
        }
    }
}