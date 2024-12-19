package com.ihsanarslan.salestracking.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ihsanarslan.salestracking.presentation.home.HomeScreen
import com.ihsanarslan.salestracking.presentation.order_list.OrderListScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val startDestination = Router.HomeScreen
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Router.HomeScreen>(
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope. SlideDirection.Left, animationSpec = tween(1000)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope. SlideDirection.Left, animationSpec = tween(1000)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope. SlideDirection.Right, animationSpec = tween(1000)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope. SlideDirection.Right, animationSpec = tween(1000)) }
        ) {
            HomeScreen(navController = navController)
        }

        composable<Router.OrderListScreen>(
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope. SlideDirection.Left, animationSpec = tween(1000)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope. SlideDirection.Left, animationSpec = tween(1000)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope. SlideDirection.Right, animationSpec = tween(1000)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope. SlideDirection.Right, animationSpec = tween(1000)) }
        ) {
            OrderListScreen(navController = navController)
        }
    }
}