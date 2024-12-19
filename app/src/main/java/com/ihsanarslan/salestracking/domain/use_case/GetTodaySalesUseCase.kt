package com.ihsanarslan.salestracking.domain.use_case

import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodaySalesUseCase @Inject constructor(
    private val orderDaoImpl: OrderDaoImpl
) {
    operator fun invoke() : Flow<Resource<Double>> {
        return orderDaoImpl.getTodaySales()
    }
}