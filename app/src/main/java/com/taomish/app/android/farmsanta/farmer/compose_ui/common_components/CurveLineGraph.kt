package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.line.config.CurveLineConfig
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing

@Composable
fun CurveLineGraph(
    points: List<Float>,
    xValues: List<String>,
    yValues: List<Float>,
    modifier: Modifier = Modifier,
) {

    val spacing = LocalSpacing.current
    if (yValues.isEmpty()) {
        return Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = str(id = R.string.no_market_data),
                style = MaterialTheme.typography.caption,
                color = Color.Cameron,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = spacing.large)
            )
        }
    }


    val graphPathPoints = mutableListOf<PointF>()
    val backgroundPathPoints = mutableListOf<PointF>()
    var lineBound by remember { mutableStateOf(0F) }
    val maxYValueState = rememberSaveable { mutableStateOf(yValues.maxOf { it }) }
    val maxYValue = maxYValueState.value
    val curveLineConfig = CurveLineConfig(hasDotMarker = true, dotColor = Color.Cameron)
    val axisConfig = AxisConfig(
        showAxis = true,
        isAxisDashed = false,
        showUnitLabels = true,
        showXLabels = true,
        xAxisColor = Color.Cameron,
        yAxisColor = Color.Cameron
    )
    val chartDimens = ChartDimens(spacing.medium)


    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = chartDimens.padding)
            .drawBehind {
                if (axisConfig.showAxis) {
                    yAxis(
                        axisConfig = axisConfig,
                        yValues = yValues.reversed()
                    )
                }
            },
        onDraw = {
            val xScaleFactor = size.width.div(points.size)
            val yScaleFactor = (size.height - 40F).div(maxYValue)
            val radius = 11.0F
            lineBound = size.width.div(points.count().times(1.2F))
//            lineBound = 58.33F

            val lineDataItems: List<Offset> = points.mapIndexed { index, data ->
                dataToOffSet(
                    index = index,
                    bound = lineBound,
                    height = size.height - 40F,
                    yValue = data,
                    scaleFactor = yScaleFactor
                )
            }.toMutableList().also {
                it.add(Offset(size.width, size.height.minus(40)))
            }


            val offsetItems: List<Offset> = mutableListOf<Offset>().apply {
                add(Offset(0F, this@Canvas.size.height.minus(20)))
                addAll(lineDataItems)
            }

            val xOffsetValues = offsetItems.map { it.x }
            val pointsPath = Path()
            offsetItems.forEachIndexed { index, offset ->
                val canDrawCircle =
                    curveLineConfig.hasDotMarker && index != 0 && index != offsetItems.size.minus(1)
                if (canDrawCircle) {
                    drawCircle(
                        color = curveLineConfig.dotColor,
                        radius = radius,
                        center = Offset(offset.x, offset.y)
                    )
                }

//                if (index != 0 && index != offsetItems.lastIndex) {
//                    this.drawContext.canvas.nativeCanvas.apply {
//                        drawText(
//                            xValues[index - 1],
//                            offset.x,
//                            size.height,
//                            Paint().apply {
//                                textSize = 20F
//                                textAlign = Paint.Align.CENTER
//                                color = Color.Cameron.toArgb()
//                                typeface = Typeface.DEFAULT_BOLD
//                            }
//                        )
//                    }
//                }

                if (index > 0) {
                    storePoints(
                        graphPathPoints,
                        backgroundPathPoints,
                        offset,
                        offsetItems[index.minus(1)]
                    )
                }
            }

            xValues.forEachIndexed { index, value ->
                this.drawContext.canvas.nativeCanvas.apply {
                    val startX = index.times(lineBound.times(1.2F))
                    val endX = index.plus(1).times(lineBound.times(1.2F))
                    drawText(
                        value,
                        (startX.plus(endX)).div(2F),
                        size.height,
                        Paint().apply {
                            textSize = 16F
                            textAlign = Paint.Align.CENTER
                            color = Color.Cameron.toArgb()
                            typeface = Typeface.DEFAULT_BOLD
                        }
                    )
                }
            }

            pointsPath.apply {
                reset()
                moveTo(offsetItems.first().x, offsetItems.first().y)
                (0.until(offsetItems.size.minus(1))).forEach { index ->
                    cubicTo(
                        graphPathPoints[index].x, graphPathPoints[index].y,
                        backgroundPathPoints[index].x, backgroundPathPoints[index].y,
                        offsetItems[index.plus(1)].x, offsetItems[index.plus(1)].y
                    )
                }
            }

            val backgroundPath = android.graphics.Path(pointsPath.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(
                        xScaleFactor.times(xOffsetValues.last()),
                        size.height.minus(yScaleFactor)
                    )
                    lineTo(xScaleFactor, size.height - yScaleFactor)
                    close()
                }

            drawPath(
                path = backgroundPath,
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Transparent),
                    endY = size.height - yScaleFactor
                ),
            )

            drawPath(
                path = pointsPath,
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Cameron, Color.Cameron),
                ),
                style = Stroke(
                    width = 5F,
                    cap = StrokeCap.Round
                )
            )
        }
    )
}

private fun storePoints(
    controlPoints1: MutableList<PointF>,
    controlPoints2: MutableList<PointF>,
    firstOffset: Offset,
    previousOffset: Offset
) {
    controlPoints1.add(
        PointF(
            (firstOffset.x + previousOffset.x) / 2,
            previousOffset.y
        )
    )
    controlPoints2.add(
        PointF(
            (firstOffset.x + previousOffset.x) / 2,
            firstOffset.y
        )
    )
}

private fun dataToOffSet(
    index: Int,
    bound: Float,
    height: Float,
    yValue: Float,
    scaleFactor: Float,
): Offset {
    val startX = index.times(bound.times(1.2F))
    val endX = index.plus(1).times(bound.times(1.2F))
    val y = height.minus(yValue.times(scaleFactor)).coerceAtLeast(0F)
    return Offset(((startX.plus(endX)).div(2F)), y)
}