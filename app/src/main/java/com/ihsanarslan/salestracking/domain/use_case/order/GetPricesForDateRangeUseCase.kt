package com.ihsanarslan.salestracking.domain.use_case.order

import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPricesForDateRangeUseCase @Inject constructor(
    private val orderDaoImpl: OrderDaoImpl
) {
    operator fun invoke(startDate: Long, endDate: Long) : Flow<Resource<List<Double>>> {
        return orderDaoImpl.getPricesForDateRange(startDate = startDate, endDate = endDate)
    }
}
