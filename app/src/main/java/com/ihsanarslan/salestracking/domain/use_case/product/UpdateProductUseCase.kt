package com.ihsanarslan.salestracking.domain.use_case.product

import com.ihsanarslan.salestracking.domain.model.ProductDto
import com.ihsanarslan.salestracking.domain.repository.ProductDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val productDaoImpl: ProductDaoImpl
) {
    suspend operator fun invoke(product : ProductDto) : Resource<Unit> {
        return productDaoImpl.update(product)
    }
}