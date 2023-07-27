package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun CloseIcon(
    iconColor: Color = Color.Cameron,
    iconSize: Dp = 16.dp,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Icon(
        painter = painterResource(id = R.drawable.ic_close),
        contentDescription = null,
        modifier = Modifier
            .padding(horizontal = spacing.small)
            .size(iconSize)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        tint = iconColor
    )
}