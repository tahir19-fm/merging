package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_zoom_image

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ZoomImageFragmentScreen(url: String) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val height = with(LocalDensity.current) { 300.dp.toPx() }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            scale = (scale * event.calculateZoom()).coerceIn(1F, 3F)
                            val offset = event.calculatePan()
                            offsetX = (offsetX + offset.x)
                                .coerceIn(
                                    -((scale - 1F).coerceIn(0F, 1F) * (size.width.toFloat() * .33F) * scale),
                                    ((scale - 1F).coerceIn(0F, 1F) * (size.width.toFloat() * .33F) * scale)
                                )
                            offsetY = (offsetY + offset.y)
                                .coerceIn(
                                    -((scale - 1F).coerceIn(0F, 1F) * (height * .33F) * scale),
                                    ((scale - 1F).coerceIn(0F, 1F) * (height * .33F) * scale)
                                )
                        } while (event.changes.any { it.pressed })
                    }
                }
            }
    ) {
        RemoteImage(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
                .height(300.dp)
                .clip(shape)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                ),
            imageLink = url,
            error = R.mipmap.img_default_pop,
            contentScale = ContentScale.FillBounds
        )
    }
}