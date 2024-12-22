package com.ihsanarslan.salestracking.presentation.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanarslan.salestracking.domain.model.OrderWithProducts
import com.ihsanarslan.salestracking.domain.use_case.order.GetOrdersWithTheOrdersProductsUseCase
import com.ihsanarslan.salestracking.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getOrdersWithTheOrdersProducts: GetOrdersWithTheOrdersProductsUseCase
) : ViewModel() {

    private val _orderList = MutableStateFlow<List<OrderWithProducts>>(emptyList())
    val orderList: StateFlow<List<OrderWithProducts>>
        get() = _orderList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()


    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            getOrdersWithTheOrdersProducts().collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Ürünler yüklenirken bir hata oluştu"
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
}