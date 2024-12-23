package com.ihsanarslan.salestracking.presentation.analysis

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
import com.ihsanarslan.salestracking.domain.model.OrderWithProducts
import com.ihsanarslan.salestracking.domain.use_case.order.GetOrdersWithTheOrdersProductsUseCase
import com.ihsanarslan.salestracking.domain.use_case.order.GetPricesForDateRangeUseCase
import com.ihsanarslan.salestracking.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getOrdersWithTheOrdersProducts: GetOrdersWithTheOrdersProductsUseCase,
    private val getPricesForDateRangeUseCase: GetPricesForDateRangeUseCase
) : ViewModel() {

    private val _linePoints = MutableStateFlow<List<Point>>(emptyList())
    val linePoints: StateFlow<List<Point>>
        get() = _linePoints.asStateFlow()

    private val _barPoints = MutableStateFlow<List<BarData>>(emptyList())
    val barPoints: StateFlow<List<BarData>>
        get() = _barPoints.asStateFlow()

    private val _dateList = MutableStateFlow<List<String>>(emptyList())
    val dateList: StateFlow<List<String>>
        get() = _dateList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()


    fun getDatesForDateRange(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            getOrdersWithTheOrdersProducts().collect { orders ->
                when (orders) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Ürünler yüklenirken bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("tr"))
                        val dates = orders.data
                            .filter { it.order.createdAt in startDate..endDate }
                            .map { orderWithProducts ->
                                dateFormat.format(orderWithProducts.order.createdAt)
                            }
                        _dateList.value = dates
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getPricesForDateRange(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            getPricesForDateRangeUseCase(startDate = startDate, endDate = endDate).collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Veriler yüklenirken bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        val linePoints = it.data.mapIndexed { index, price ->
                            Point(x = index.toFloat(), y = price.toFloat())
                        }
                        _linePoints.value = linePoints

                        val barPoints = it.data.mapIndexed { index, price ->
                            BarData(
                                label = (index + 1).toString(), // Örnek bir etiket, özelleştirebilirsiniz
                                point = Point(x = index.toFloat(), y = price.toFloat()),
                                color = Color(0xFF23af92)
                            )
                        }
                        _barPoints.value = barPoints
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}
