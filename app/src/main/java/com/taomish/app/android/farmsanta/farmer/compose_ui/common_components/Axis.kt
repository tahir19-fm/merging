package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.himanshoe.charty.common.axis.AxisConfig
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import java.text.DecimalFormat


internal fun DrawScope.yAxis(axisConfig: AxisConfig, yValues: List<Float>) {
    if (yValues.isEmpty()) return
    val graphYAxisEndPoint = (size.height - 40).div(yValues.size)
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)

    yValues.forEachIndexed { index, unit ->
        val yAxisEndPoint = graphYAxisEndPoint.times(index)
        if (axisConfig.showUnitLabels) {
            drawIntoCanvas {
                it.nativeCanvas.apply {
                    drawText(
                        getLabelText(unit),
                        0F.minus(25),
                        yAxisEndPoint.minus(10),
                        Paint().apply {
                            textSize = 20F
                            textAlign = Paint.Align.CENTER
                            color = Color.Cameron.toArgb()
                            typeface = Typeface.DEFAULT_BOLD
                        }
                    )
                }
            }
        }
        if (index != 0) {
            drawLine(
                start = Offset(x = 0f, y = yAxisEndPoint),
                end = Offset(x = size.width, y = yAxisEndPoint),
                color = axisConfig.xAxisColor,
                pathEffect = if (axisConfig.isAxisDashed) pathEffect else null,
                alpha = 0.1F,
                strokeWidth = 2.8F
            )
        }
    }
}


internal fun DrawScope.xAxis(axisConfig: AxisConfig, xValues: List<String>) {
    require(xValues.isNotEmpty()) { "yValues should not be empty" }
    val graphXAxisEndPoint = size.width.div(xValues.size)

    xValues.forEachIndexed { index, label ->
        val xAxisEndPoint = graphXAxisEndPoint.times(index + 1)
        if (axisConfig.showUnitLabels) {
            drawIntoCanvas {
                it.nativeCanvas.apply {
                    drawText(
                        label,
                        xAxisEndPoint.minus(10),
                        size.height.plus(25),
                        Paint().apply {
                            textSize = size.width.div(30)
                            textAlign = Paint.Align.CENTER
                            color = Color.Cameron.toArgb()
                        }
                    )
                }
            }
        }
    }
}



private fun getLabelText(value: Float) = DecimalFormat("#").format(value).toString()