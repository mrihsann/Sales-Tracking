package com.ihsanarslan.salestracking.presentation.add_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanarslan.salestracking.domain.model.ProductsDto
import com.ihsanarslan.salestracking.domain.use_case.InsertProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val insertProductUseCase: InsertProductUseCase
) : ViewModel() {

    fun insert(name:String,description:String,price:String){
        viewModelScope.launch {
            if (price.toDoubleOrNull() != null){
                insertProductUseCase(
                    ProductsDto(
                        name = name,
                        description = description,
                        price = price.toDouble()
                    )
                )
            }
        }
    }
}