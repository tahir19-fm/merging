package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import dashedBorder


@Composable
fun DottedDivider(color: Color, padding: Dp = LocalSpacing.current.zero) {
    val shape = LocalShapes.current.smallShape
    Divider(
        color = Color.Transparent,
        modifier = Modifier
            .padding(horizontal = padding)
            .dashedBorder(
                width = 1.dp,
                color = color,
                shape = shape,
                on = 3.dp,
                off = 6.dp
            )
    )
}