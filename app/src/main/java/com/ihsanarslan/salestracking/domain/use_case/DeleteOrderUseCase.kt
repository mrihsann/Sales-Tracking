package com.ihsanarslan.salestracking.domain.use_case

import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import javax.inject.Inject

class DeleteOrderUseCase @Inject constructor(
    private val orderDaoImpl: OrderDaoImpl
) {
    suspend operator fun invoke(id : Int) : Resource<Unit> {
        return orderDaoImpl.delete(id)
    }
}