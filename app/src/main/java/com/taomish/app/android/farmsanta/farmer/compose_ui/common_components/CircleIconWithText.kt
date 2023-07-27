package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun CircleIconWithText(
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    iconSize: Dp = 40.dp,
    text: String,
    textPadding: Dp = LocalSpacing.current.small,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    tint: Color,
    textColor: Color = tint,
    borderColor: Color? = tint,
    onClick: () -> Unit,
) {
    val spacing = LocalSpacing.current

    if (borderColor != null) {
        modifier
            .size(iconSize)
            .clip(CircleShape)
            .border(
                width = 0.3.dp,
                color = borderColor,
                shape = CircleShape
            )
            .padding(spacing.small)
    } else {
        modifier
            .padding(spacing.small)
            .size(iconSize)
    }

    Column(
        modifier = containerModifier
            .padding(spacing.medium)
            .clickable(
                indication = rememberRipple(
                    bounded = false,
                    color = Color.Cameron.copy(alpha = .2f)
                ),
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = tint,
            modifier = modifier.size(iconSize)
        )

        Text(
            text = text,
            color = if (tint == Color.Unspecified || tint == Color.Transparent) Color.Cameron else textColor,
            style = textStyle,
            modifier = Modifier.padding(vertical = textPadding),
            textAlign = TextAlign.Center
        )
    }
}