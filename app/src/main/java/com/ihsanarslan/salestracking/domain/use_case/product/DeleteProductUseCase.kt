package com.ihsanarslan.salestracking.domain.use_case.product

import com.ihsanarslan.salestracking.domain.repository.ProductDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productDaoImpl: ProductDaoImpl
) {
    suspend operator fun invoke(id : Int) : Resource<Unit> {
        return productDaoImpl.delete(id)
    }
}