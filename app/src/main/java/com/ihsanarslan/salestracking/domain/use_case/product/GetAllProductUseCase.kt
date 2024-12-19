package com.ihsanarslan.salestracking.domain.use_case.product

import com.ihsanarslan.salestracking.domain.model.ProductDto
import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.domain.repository.ProductDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val productDaoImpl: ProductDaoImpl
) {
    operator fun invoke() : Flow<Resource<List<ProductDto>>> {
        return productDaoImpl.getAll()
    }
}