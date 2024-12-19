package com.ihsanarslan.salestracking.domain.use_case

import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLast10OrdersUseCase @Inject constructor(
    private val orderDaoImpl: OrderDaoImpl
) {
    operator fun invoke() : Flow<Resource<List<OrderDto>>> {
        return orderDaoImpl.getLast10Orders()
    }
}