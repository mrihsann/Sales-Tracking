package com.ihsanarslan.salestracking.presentation.order_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.use_case.DeleteOrderUseCase
import com.ihsanarslan.salestracking.domain.use_case.GetOrdersBetweenDatesUseCase
import com.ihsanarslan.salestracking.domain.use_case.InsertOrderUseCase
import com.ihsanarslan.salestracking.domain.use_case.UpdateOrderUseCase
import com.ihsanarslan.salestracking.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val getOrdersBetweenDatesUseCase: GetOrdersBetweenDatesUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val insertOrderUseCase: InsertOrderUseCase
) : ViewModel() {

    private val _orderList = MutableStateFlow<List<OrderDto>>(emptyList())
    val orderList: StateFlow<List<OrderDto>>
        get() = _orderList.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()


    fun getOrdersBetweenDates(startDate : Long, endDate : Long) {
        viewModelScope.launch {
            getOrdersBetweenDatesUseCase(startDate = startDate, endDate = endDate).collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _orderList.value = it.data
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun insert(name:String,description:String,price:String){
        viewModelScope.launch {
            if (price.toDoubleOrNull() != null){
                insertOrderUseCase(
                    OrderDto(
                        name = name,
                        description = description,
                        price = price.toDouble()
                    )
                )
            }
        }
    }

    fun delete(orderId : Int) {
        viewModelScope.launch {

            val result = deleteOrderUseCase(orderId)
            when (result) {
                is Resource.Loading -> _isLoading.value = true
                is Resource.Success -> {
                    _errorMessage.value = null
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    _errorMessage.value = "Sipariş silinirken bir hata oluştu"
                    _isLoading.value = false
                }
            }
        }
    }

    fun update(order: OrderDto) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = updateOrderUseCase(order)
            when (result) {
                is Resource.Loading -> _isLoading.value = true
                is Resource.Success -> {
                    _errorMessage.value = null
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    _errorMessage.value = "Sipariş güncellenirken bir hata oluştu"
                    _isLoading.value = false
                }
            }
        }
    }
}