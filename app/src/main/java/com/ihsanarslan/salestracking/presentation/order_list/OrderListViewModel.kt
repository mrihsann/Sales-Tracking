package com.ihsanarslan.salestracking.presentation.order_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanarslan.salestracking.domain.model.OrderWithProducts
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.model.ProductDto
import com.ihsanarslan.salestracking.domain.use_case.order.DeleteOrderUseCase
import com.ihsanarslan.salestracking.domain.use_case.order.GetOrdersWithTheOrdersProductsUseCase
import com.ihsanarslan.salestracking.domain.use_case.order.GetOrdersBetweenDatesUseCase
import com.ihsanarslan.salestracking.domain.use_case.order.InsertOrderUseCase
import com.ihsanarslan.salestracking.domain.use_case.order.UpdateOrderUseCase
import com.ihsanarslan.salestracking.domain.use_case.product.GetAllProductUseCase
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
    private val insertOrderUseCase: InsertOrderUseCase,
    private  val getAllProductUseCase: GetAllProductUseCase,
    private val getOrdersWithTheOrdersProducts: GetOrdersWithTheOrdersProductsUseCase
) : ViewModel() {

    private val _orderList = MutableStateFlow<List<OrderDto>>(emptyList())
    val orderList: StateFlow<List<OrderDto>>
        get() = _orderList.asStateFlow()

    private val _productList = MutableStateFlow<List<ProductDto>>(emptyList())
    val productList: StateFlow<List<ProductDto>>
        get() = _productList.asStateFlow()

    private val _ordersWithProducts = MutableStateFlow<List<OrderWithProducts>>(emptyList())
    val ordersWithProducts: StateFlow<List<OrderWithProducts>>
        get() = _ordersWithProducts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()


    init {
        getAllProduct()
        getOrdersWithProducts()
    }


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

    fun insert(order: OrderDto, products: Map<Int, Int>) {
        viewModelScope.launch {
            try {
                // Siparişi ve ürünleri ekle
                insertOrderUseCase(
                    OrderDto(
                        name = order.name,
                        description = order.description,
                        price = order.price

                    ),
                    products = products
                )

            } catch (e: Exception) {
                // Hata durumunda errorMessaging'e yazı gönder
                _errorMessage.value = "Beklenmeyen bir hata oluştu: ${e.message}"
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

    fun update(order: OrderDto, products: Map<Int, Int>) {
        viewModelScope.launch {
            val result = updateOrderUseCase(order,products)
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

    private fun getAllProduct() {
        viewModelScope.launch {
            getAllProductUseCase().collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Ürünler yüklenirken bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _productList.value = it.data
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private fun getOrdersWithProducts() {
        viewModelScope.launch {
            getOrdersWithTheOrdersProducts().collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "Ürünler yüklenirken bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _ordersWithProducts.value = it.data
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}