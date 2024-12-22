package com.ihsanarslan.salestracking.presentation.order_list

import android.util.Range
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.presentation.order_list.components.CalendarComponent
import com.ihsanarslan.salestracking.presentation.order_list.components.AddOrUpdateOrderSheetContent
import com.ihsanarslan.salestracking.presentation.order_list.components.OrderItem
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeEventDefaults
import com.pushpal.jetlime.JetLimeExtendedEvent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeApi::class)
@Composable
fun OrderListScreen(
    navController: NavController
){

    val vm = hiltViewModel<OrderListViewModel>()
    val orders = vm.orderList.collectAsStateWithLifecycle()
    val ordersWithProducts = vm.ordersWithProducts.collectAsStateWithLifecycle()
    val products = vm.productList.collectAsStateWithLifecycle()
    val loading = vm.isLoading.collectAsStateWithLifecycle()
    val error = vm.errorMessage.collectAsStateWithLifecycle()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var orderToDelete by remember { mutableStateOf<Int?>(null) }

    var selectedOrder by remember { mutableStateOf<OrderDto?>(null) }

    val selectedRange = remember {
        mutableStateOf(
            Range(LocalDate.now(), LocalDate.now())
        )
    }
    var showDateRangeDialog by remember { mutableStateOf(false) }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = selectedRange.value) {
        val startDate = selectedRange.value.lower
        val endDate = selectedRange.value.upper

        vm.getOrdersBetweenDates(
            startDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            endDate = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    BottomSheetScaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Sipariş Geçmişi") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDateRangeDialog=true }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "DateRange")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            AddOrUpdateOrderSheetContent(
                order = ordersWithProducts.value.find { it.order.orderId == selectedOrder?.orderId },
                products = products.value,
                onSave = { id, name, description, price, products ->
                    if (selectedOrder == null) {
                        vm.insert(
                            order = OrderDto(
                                name= name,
                                description= description,
                                price = price.toDouble()
                            ),
                            products = products
                        )
                    } else {
                        vm.update(
                            order = OrderDto(
                                orderId = id,
                                name= name,
                                description= description,
                                price = price.toDouble()
                            ),
                            products = products
                        )
                    }
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            )
        },
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetShadowElevation = 8.dp,
    ) { innerPadding ->
        Box (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            Column {
                JetLimeColumn(
                    modifier = Modifier.padding(16.dp),
                    itemsList = ItemsList(orders.value),
                    key = { _, item -> item.orderId },
                ) { _, item, position ->
                    JetLimeExtendedEvent(
                        style = JetLimeEventDefaults.eventStyle(
                            position = position
                        ),
                        additionalContent = {
                            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val formattedTime = item.createdAt?.let { Date(it) }
                                ?.let { formatter.format(it) }

                            if (formattedTime != null) {
                                Text(text = formattedTime)
                            }
                        }
                    ) {
                        OrderItem(
                            order = item,
                            onDeleteClick = {
                                showDeleteDialog = true
                                orderToDelete = item.orderId
                            },
                            onEditClick = {
                                selectedOrder = item
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        )
                    }
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.dp),
                onClick = {
                selectedOrder = null
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    }
    if (showDateRangeDialog) {
        CalendarComponent(
            closeSelection = { showDateRangeDialog = false },
            selectedRange = selectedRange
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Siparişi Sil") },
            text = { Text(text = "Bu siparişi silmek istediğinizden emin misiniz?") },
            confirmButton = {
                TextButton(onClick = {
                    orderToDelete?.let {
                        vm.delete(it)
                    }
                    showDeleteDialog = false
                }) {
                    Text(text = "Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                }) {
                    Text(text = "Hayır")
                }
            }
        )
    }
}