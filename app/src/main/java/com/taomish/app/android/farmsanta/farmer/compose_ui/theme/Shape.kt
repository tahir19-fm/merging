package com.taomish.app.android.farmsanta.farmer.compose_ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

data class CustomShapes(
    val mediumShape: CornerBasedShape = RoundedCornerShape(8.dp),
    val barShape: CornerBasedShape = RoundedCornerShape(12.dp),
    val imageShape: CornerBasedShape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
    val smallCardShape: CornerBasedShape = RoundedCornerShape(16.dp),
    val mediumCardShape: CornerBasedShape = RoundedCornerShape(24.dp),
    val largeCardShape: CornerBasedShape = RoundedCornerShape(32.dp),
    val smallShape: CornerBasedShape = RoundedCornerShape(4.dp),
    val largeShape: CornerBasedShape = RoundedCornerShape(24.dp),
    val topRoundedSmallShape: CornerBasedShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
    val topRoundedMediumShape: CornerBasedShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp
    ),
    val topRoundedLargeShape: CornerBasedShape = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp
    ),
    val bottomRoundedShape: CornerBasedShape = RoundedCornerShape(
        bottomStart = 4.dp,
        bottomEnd = 4.dp
    ),
)

val LocalShapes = compositionLocalOf { CustomShapes() }