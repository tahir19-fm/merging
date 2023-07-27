package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun MoreVertIcon(
    modifier: Modifier = Modifier,
    iconColor: Color = Color.LightGray,
    iconSize: Dp = 32.dp,
    onMoreOptionsClicked: () -> Unit
) {
    Icon(
        imageVector = Icons.Filled.MoreVert,
        contentDescription = null,
        tint = iconColor,
        modifier = modifier
            .size(iconSize)
            .clip(CircleShape)
            .clickable { onMoreOptionsClicked() }
    )
}