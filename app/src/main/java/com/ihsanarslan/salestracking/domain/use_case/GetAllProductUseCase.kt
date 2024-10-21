package com.ihsanarslan.salestracking.domain.use_case

import com.ihsanarslan.salestracking.domain.model.ProductsDto
import com.ihsanarslan.salestracking.domain.repository.ProductsDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val productsDaoImpl: ProductsDaoImpl
) {
    operator fun invoke() : Flow<Resource<List<ProductsDto>>> {
        return productsDaoImpl.getAll()
    }
}