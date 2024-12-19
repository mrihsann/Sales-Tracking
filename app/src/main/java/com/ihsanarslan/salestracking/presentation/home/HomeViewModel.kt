package com.ihsanarslan.salestracking.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.use_case.order.GetLast10OrdersUseCase
import com.ihsanarslan.salestracking.domain.use_case.order.GetLastDaysPricesUseCase
import com.ihsanarslan.salestracking.domain.use_case.order.GetTodaySalesUseCase
import com.ihsanarslan.salestracking.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLastDaysPricesUseCase: GetLastDaysPricesUseCase,
    private val getLast10OrdersUseCase : GetLast10OrdersUseCase,
    private val getTodaySalesUseCase: GetTodaySalesUseCase
): ViewModel() {

    private val _priceList = MutableStateFlow<List<Double>>(emptyList())
    val priceList: StateFlow<List<Double>>
        get() = _priceList.asStateFlow()

    private val _last10Orders = MutableStateFlow<List<OrderDto>>(emptyList())
    val last10Orders: StateFlow<List<OrderDto>>
        get() = _last10Orders.asStateFlow()

    private val _todaySales = MutableStateFlow<Double>(0.0)
    val todaySales: StateFlow<Double>
        get() = _todaySales.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()

    init {
        getLast30DaysPrices()
        getLast10Orders()
        getTodaySales()
    }

    private fun getLast30DaysPrices() {
        viewModelScope.launch {
            val thirtyDaysAgo = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000
            getLastDaysPricesUseCase(startDate = thirtyDaysAgo).collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _priceList.value = it.data
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private fun getLast10Orders() {
        viewModelScope.launch {
            getLast10OrdersUseCase().collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _last10Orders.value = it.data
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private fun getTodaySales() {
        viewModelScope.launch {
            getTodaySalesUseCase().collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _todaySales.value = it.data
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}