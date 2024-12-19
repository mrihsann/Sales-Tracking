package com.ihsanarslan.salestracking.presentation.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RevenueDaily(todaySales : Double){
    Text(text = "$todaySales")
}