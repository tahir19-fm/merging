package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    text: String,
    textColor: Color = Color.White,
    textPadding: Dp = LocalSpacing.current.small,
    textStyle: TextStyle = LocalTextStyle.current,
    isClickable: Boolean = false,
    backgroundColor: Color,
    onClick: () -> Unit = { }
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                enabled = isClickable,
                onClick = { onClick() }
            ),
        elevation = spacing.small,
        shape = CircleShape,
        color = backgroundColor
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.invoke()

            Text(
                text = text,
                modifier = Modifier
                    .padding(textPadding),
                style = textStyle,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            trailingIcon?.invoke()
        }
    }
}

@Composable
fun ChipIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    iconColor: Color,
    backgroundShape: Shape? = null,
    backgroundColor: Color? = null,
    size: Dp,
    onClick: (() -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = null,
        modifier = (if (backgroundShape != null && backgroundColor != null) modifier
            .padding(spacing.small)
            .background(color = backgroundColor, shape = backgroundShape)
        else modifier)
            .padding(spacing.small)
            .clip(CircleShape)
            .clickable { onClick?.invoke() }
            .size(size),
        tint = iconColor
    )
}