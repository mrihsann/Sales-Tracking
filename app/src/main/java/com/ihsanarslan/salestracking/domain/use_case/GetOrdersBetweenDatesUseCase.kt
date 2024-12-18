package com.ihsanarslan.salestracking.domain.use_case

import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersBetweenDatesUseCase @Inject constructor(
    private val orderDaoImpl: OrderDaoImpl
) {
    operator fun invoke(startDate: Long, endDate: Long) : Flow<Resource<List<OrderDto>>> {
        return orderDaoImpl.getOrdersBetweenDates(startDate = startDate, endDate = endDate)
    }
}