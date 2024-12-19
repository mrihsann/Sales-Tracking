package com.ihsanarslan.salestracking.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ihsanarslan.salestracking.navigation.Router
import com.ihsanarslan.salestracking.presentation.home.components.OrderHistory
import com.ihsanarslan.salestracking.presentation.home.components.RevenueChange
import com.ihsanarslan.salestracking.presentation.home.components.RevenueDaily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
){

    val vm = hiltViewModel<HomeViewModel>()
    val priceList = vm.priceList.collectAsStateWithLifecycle()
    val last10Orders = vm.last10Orders.collectAsStateWithLifecycle()
    val todaySales = vm.todaySales.collectAsStateWithLifecycle()
    val loading = vm.isLoading.collectAsStateWithLifecycle()
    val error = vm.errorMessage.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Ana Sayfa")
            })
        }
    ){
        Column (modifier = Modifier.padding(it)){
            RevenueDaily(
                todaySales = todaySales.value
            )
            RevenueChange(
                priceList = priceList.value,
                navigateToDetail = {
                    navController.navigate(Router.OrderListScreen)
                }
            )
            OrderHistory(
                orders = last10Orders.value,
                navigateToDetail = {
                    navController.navigate(Router.OrderListScreen)
                }
            )
        }
    }
}
