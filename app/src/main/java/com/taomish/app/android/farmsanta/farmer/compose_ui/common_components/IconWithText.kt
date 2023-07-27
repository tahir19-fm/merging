package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun IconWithText(
    @DrawableRes iconId: Int,
    iconSize: Dp = 16.dp,
    tint: Color = Color.White,
    text: String,
    textColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.overline
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier.padding(vertical = spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )

        Text(
            text = text,
            color = textColor,
            style = textStyle,
            modifier = Modifier.padding(start = spacing.extraSmall)
        )
    }
}