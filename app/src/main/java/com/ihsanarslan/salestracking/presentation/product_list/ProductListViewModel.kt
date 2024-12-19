package com.ihsanarslan.salestracking.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.model.ProductDto
import com.ihsanarslan.salestracking.domain.use_case.product.DeleteProductUseCase
import com.ihsanarslan.salestracking.domain.use_case.product.GetAllProductUseCase
import com.ihsanarslan.salestracking.domain.use_case.product.InsertProductUseCase
import com.ihsanarslan.salestracking.domain.use_case.product.UpdateProductUseCase
import com.ihsanarslan.salestracking.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val insertProductUseCase: InsertProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private  val getAllProductUseCase: GetAllProductUseCase
) : ViewModel() {

    private val _productList = MutableStateFlow<List<ProductDto>>(emptyList())
    val productList: StateFlow<List<ProductDto>>
        get() = _productList.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()


    init {
        getAll()
    }

    fun insert(name:String,description:String,price:String){
        viewModelScope.launch {
            if (price.toDoubleOrNull() != null){
                insertProductUseCase(
                    ProductDto(
                        name = name,
                        description = description,
                        price = price.toDouble()
                    )
                )
            }
        }
    }

    fun delete(productId : Int) {
        viewModelScope.launch {

            val result = deleteProductUseCase(productId)
            when (result) {
                is Resource.Loading -> _isLoading.value = true
                is Resource.Success -> {
                    _errorMessage.value = null
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    _errorMessage.value = "Ürün silinirken bir hata oluştu"
                    _isLoading.value = false
                }
            }
        }
    }

    fun update(product: ProductDto) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = updateProductUseCase(product)
            when (result) {
                is Resource.Loading -> _isLoading.value = true
                is Resource.Success -> {
                    _errorMessage.value = null
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    _errorMessage.value = "Ürün güncellenirken bir hata oluştu"
                    _isLoading.value = false
                }
            }
        }
    }

    private fun getAll() {
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
                        println(it.data)
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}