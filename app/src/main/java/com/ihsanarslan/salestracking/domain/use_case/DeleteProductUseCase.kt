package com.ihsanarslan.salestracking.domain.use_case

import com.ihsanarslan.salestracking.domain.model.ProductsDto
import com.ihsanarslan.salestracking.domain.repository.ProductsDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productsDaoImpl: ProductsDaoImpl
) {
    suspend operator fun invoke(id : Int) : Resource<Unit> {
        return productsDaoImpl.delete(id)
    }
}