package com.ihsanarslan.salestracking.domain.use_case.order

import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import javax.inject.Inject

class InsertOrderUseCase @Inject constructor(
    private val orderDaoImpl: OrderDaoImpl
) {
    suspend operator fun invoke(
        order : OrderDto,
        products: Map<Int, Int>
    ): Resource<Unit> {
        return orderDaoImpl.insert(order, products)
    }
}