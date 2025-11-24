package com.hydrosense.corp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import com.hydrosense.corp.ui.theme.*

@Composable
fun DotChart(
    data: List<Pair<Int, Int>>, // X = index, Y = value
    labels: List<String> = emptyList(), // time labels
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
    barColor: Color = AccentRed
) {
    if (data.isEmpty()) return

    val maxY = data.maxOfOrNull { it.second }?.let { (it * 1.2).roundToInt().coerceAtLeast(1) } ?: 10

    Canvas(modifier = modifier) {
        val chartHeight = size.height
        val chartWidth = size.width

        val paddingLeft = 40f
        val paddingBottom = 40f

        val usableHeight = chartHeight - paddingBottom
        val usableWidth = chartWidth - paddingLeft

        val barWidth = if (data.size > 1) usableWidth / data.size * 0.6f else usableWidth * 0.6f
        val xStep = if (data.size > 1) usableWidth / (data.size - 1) else usableWidth

//        // Draw Y-axis
//        drawLine(
//            color = AccentGreen,
//            start = Offset(paddingLeft, 0f),
//            end = Offset(paddingLeft, usableHeight)
//        )
//
//        // Draw X-axis
//        drawLine(
//            color = AccentPurple,
//            start = Offset(paddingLeft, usableHeight),
//            end = Offset(chartWidth, usableHeight)
//        )

        // Draw bars
        data.forEachIndexed { index, pair ->
            val x = paddingLeft + index * xStep - barWidth / 2
            val barHeight = (pair.second.toFloat() / maxY) * usableHeight
            val yTop = usableHeight - barHeight

            drawRect(
                color = barColor,
                topLeft = Offset(x, yTop),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
            )
        }

        // Draw Y-axis labels
        val step = maxY / 5
        for (i in 0..5) {
            val value = i * step
            val y = usableHeight - (value.toFloat() / maxY) * usableHeight
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    value.toString(),
                    paddingLeft - 50f,
                    y + 50f,
                    android.graphics.Paint().apply {
                        textAlign = android.graphics.Paint.Align.RIGHT
                        textSize = 30f
                        color = android.graphics.Color.WHITE
                    }
                )
            }
        }

        // Draw X-axis labels
        labels.forEachIndexed { index, label ->
            val x = paddingLeft + index * xStep
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    label,
                    x,
                    chartHeight - 0f,
                    android.graphics.Paint().apply {
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 25f
                        color = android.graphics.Color.WHITE
                    }
                )
            }
        }
    }
}
