package com.ihsanarslan.salestracking.presentation.list_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanarslan.salestracking.domain.model.ProductsDto
import com.ihsanarslan.salestracking.domain.use_case.GetAllProductUseCase
import com.ihsanarslan.salestracking.domain.use_case.InsertProductUseCase
import com.ihsanarslan.salestracking.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    private val getAllProductUseCase: GetAllProductUseCase
) : ViewModel() {

    private val _productList = MutableStateFlow<List<ProductsDto>>(emptyList())
    val productList: StateFlow<List<ProductsDto>>
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

    private fun getAll() {
        viewModelScope.launch {
            getAllProductUseCase().collect {
                when (it) {
                    is Resource.Error -> _errorMessage.value = "Bir hata oluştu"
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Success -> _productList.value = it.data
                }
            }
        }
    }
}