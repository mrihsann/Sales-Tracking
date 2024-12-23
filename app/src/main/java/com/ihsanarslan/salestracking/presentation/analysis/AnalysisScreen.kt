package com.ihsanarslan.salestracking.presentation.analysis

import android.util.Range
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ihsanarslan.salestracking.presentation.analysis.components.LineChartComponent
import com.ihsanarslan.salestracking.presentation.order_list.components.CalendarComponent
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    navController: NavController
){

    val vm = hiltViewModel<AnalysisViewModel>()
    val linePoints = vm.linePoints.collectAsStateWithLifecycle()
    val barPoints = vm.barPoints.collectAsStateWithLifecycle()
    val dateList = vm.dateList.collectAsStateWithLifecycle()
    val loading = vm.isLoading.collectAsStateWithLifecycle()
    val error = vm.errorMessage.collectAsStateWithLifecycle()
    val selectedRange = remember {
        mutableStateOf(
            Range(
                LocalDate.of(2019, 12,23),
                LocalDate.now(ZoneId.systemDefault())
            )
        )
    }
    var showDateRangeDialog by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = selectedRange.value) {
        val startDate = selectedRange.value.lower
        val endDate = selectedRange.value.upper

        vm.getPricesForDateRange(
            startDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            endDate = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )

        vm.getDatesForDateRange(
            startDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            endDate = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    BottomSheetScaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Analiz") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDateRangeDialog=true }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Date Range",tint= MaterialTheme.colorScheme.onBackground)
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

        },
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetShadowElevation = 8.dp,
    ) { innerPadding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){
            if (linePoints.value.isNotEmpty() and dateList.value.isNotEmpty() ){
                LineChartComponent(
                    modifier= Modifier.fillMaxSize(),
                    linePoints = linePoints.value,
                    dateList = dateList.value
                )
            }else{
                Text(text = "Boş veri")
            }
            
        }
    }

    if (showDateRangeDialog) {
        CalendarComponent(
            closeSelection = { showDateRangeDialog = false },
            selectedRange = selectedRange
        )
    }
}