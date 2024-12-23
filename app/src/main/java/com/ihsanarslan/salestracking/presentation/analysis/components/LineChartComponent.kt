package com.ihsanarslan.salestracking.presentation.analysis.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

@Composable
fun LineChartComponent(
    modifier: Modifier = Modifier,
    linePoints : List<Point>,
    dateList : List<String>
){
    val steps = 10

    val minY = linePoints.minOfOrNull { it.y } ?: 0f
    val maxY = linePoints.maxOfOrNull { it.y } ?: 0f

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .bottomPadding(40.dp)
        .backgroundColor(Color.Transparent)
        .steps(linePoints.size - 1)
        .labelData { i -> dateList[i] }
        .labelAndAxisLinePadding(15.dp)
        .axisLabelAngle(20f)
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { i ->
            val yScale = (maxY - minY) / steps
            (minY + (i * yScale)).formatToSinglePrecision()
        }
        .build()


    val lines = listOf(
        Line(
            dataPoints = linePoints,
            LineStyle(
                color = Color(0xFF23af92),
                lineType = LineType.SmoothCurve()
            ),
            IntersectionPoint(
                color = Color(0xFF23af92)
            ),
            SelectionHighlightPoint(color = Color(0xFF23af92)),
            ShadowUnderLine(
                alpha = 0.5f,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF23af92),
                        Color.Transparent
                    )
                )
            ),
            SelectionHighlightPopUp(
                popUpLabel = { x, y ->
                    val xLabel = "x : ${dateList[x.toInt()]} "
                    val yLabel = "y : ${String.format("%.2f", y)}"
                    "$xLabel $yLabel"
                }
            )
        )
    )

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = lines,
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        bottomPadding = 30.dp,
        gridLines = GridLines(
            color = MaterialTheme.colorScheme.outlineVariant
        ),
        backgroundColor = MaterialTheme.colorScheme.background
    )

    LineChart(
        modifier = modifier,
        lineChartData = lineChartData
    )
}