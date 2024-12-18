package com.ihsanarslan.salestracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ihsanarslan.salestracking.presentation.order_list.OrderListScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("OrderList") {
            OrderListScreen()
        }
    }
}