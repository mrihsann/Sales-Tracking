package com.ihsanarslan.salestracking.domain.use_case.order

import com.ihsanarslan.salestracking.domain.model.OrderWithProducts
import com.ihsanarslan.salestracking.domain.repository.OrderDaoImpl
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersWithTheOrdersProductsUseCase @Inject constructor(
    private val orderProductDao: OrderDaoImpl
) {
    operator fun invoke(): Flow<Resource<List<OrderWithProducts>>> {
        return orderProductDao.getOrdersWithTheOrdersProducts()
    }
}
