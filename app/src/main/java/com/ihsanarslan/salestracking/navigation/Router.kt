package com.ihsanarslan.salestracking.navigation

import kotlinx.serialization.Serializable

sealed class Router {

    @Serializable
    data object HomeScreen : Router()

    @Serializable
    data object OrderListScreen : Router()

    @Serializable
    data object ProductListScreen : Router()
}