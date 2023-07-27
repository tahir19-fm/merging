package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import android.graphics.drawable.PictureDrawable
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.caverock.androidsvg.SVG
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun SVG(modifier: Modifier = Modifier, @RawRes fileName: Int, params: SVG.() -> Unit = {}) {
    val context = LocalContext.current
    val svg = SVG.getFromResource(context, fileName)
    svg.params()
    val drawable = PictureDrawable(svg.renderToPicture())
    val spacing = LocalSpacing.current
//    var bounds by remember { mutableStateOf(RectF(0f, 0f, 48f, 48f)) }

    Image(
        painter = rememberDrawablePainter(drawable = drawable),
        contentDescription = null,
        modifier = modifier.padding(spacing.small),
        contentScale = ContentScale.FillBounds
    )
}