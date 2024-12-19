package com.ihsanarslan.salestracking.presentation.home.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line


@Composable
fun RevenueChange(
    priceList : List<Double>,
    navigateToDetail : () -> Unit
){

    val lines = listOf(
        Line(
            label = "",
            values = priceList,
            color = SolidColor(Color(0xFF23af92)),
            firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
            secondGradientFillColor = Color.Transparent,
            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
            gradientAnimationDelay = 1000,
            drawStyle = DrawStyle.Stroke(width = 2.dp),
        )
    )

    OutlinedCard(modifier = Modifier
        .fillMaxWidth()){
        Column(modifier = Modifier.padding(15.dp)) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Text(text = "Ciro Artış Takibi", style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = navigateToDetail) {
                    Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                labelHelperProperties = LabelHelperProperties(
                    enabled = false
                ),
                indicatorProperties =  HorizontalIndicatorProperties(
                    textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                ),
                gridProperties = GridProperties(
                    enabled = false
                ),
                data = lines,
                animationMode = AnimationMode.Together(delayBuilder = {
                    it * 500L
                }),
            )
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 30.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//                Column{
//                    Text(text = "Ortalama Kazanç", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
//                    Text(text = "Son 30 Gün", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
//                    Spacer(modifier = Modifier.height(15.dp))
//                    Text(
//                        text = "254",
//                        style = MaterialTheme.typography.titleLarge,
//                        color = MaterialTheme.colorScheme.onSurface)
//                }
//                Column{
//                    Text(text = "Değişim", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
//                    Text(text = "Son 7 Gün", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
//                    Spacer(modifier = Modifier.height(15.dp))
//                    Text(
//                        text = "210",
//                        style = MaterialTheme.typography.titleLarge,
////                        color = if (uiState.resultChangeLast7Days > 0) Color.Green else Color.Red
//                        color = Color.Green
//                    )
//                }
//            }
        }
    }
}

@Preview
@Composable
fun RevenueChangepre(){
//    RevenueChange()
}