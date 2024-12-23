package com.ihsanarslan.salestracking.domain.repository

import com.ihsanarslan.salestracking.data.entity.OrderEntity
import com.ihsanarslan.salestracking.domain.model.OrderWithProducts
import com.ihsanarslan.salestracking.data.entity.toDto
import com.ihsanarslan.salestracking.data.local.database.dao.OrderDao
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.model.toEntity
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderDaoImpl @Inject constructor(
    private val orderDao: OrderDao
){

    suspend fun insert(order : OrderDto, products: Map<Int, Int>): Resource<Unit> {
        return try {
            Resource.Loading
            orderDao.addOrderWithProducts(
                order = OrderEntity(
                    name = order.name,
                    description = order.description,
                    price = order.price
                ),
                products = products
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun delete(id: Int) : Resource<Unit> {
        return try {
            Resource.Loading
            orderDao.deleteOrderWithProducts(id)
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    suspend fun update(order: OrderDto, products: Map<Int, Int>): Resource<Unit> {
        return try {
            Resource.Loading
            val orderEntity = order.toEntity()
            orderDao.updateOrderWithProducts(orderEntity, products)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun getOrdersWithTheOrdersProducts(): Flow<Resource<List<OrderWithProducts>>> = flow {
        try {
            emit(Resource.Loading)
            val getOrdersWithTheOrdersProducts = orderDao.getOrdersWithTheOrdersProducts()
            getOrdersWithTheOrdersProducts.collect { orderWithProducts ->
                emit(Resource.Success(orderWithProducts))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }


    fun getLast10Orders(): Flow<Resource<List<OrderDto>>> = flow {
        try {
            emit(Resource.Loading)
            val order = orderDao.getLast10Orders()
            order.collect { list->
                emit(Resource.Success(list.map { it.toDto() }))
            }
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }

    fun getOrdersBetweenDates(startDate: Long, endDate: Long): Flow<Resource<List<OrderDto>>> = flow {
        try {
            emit(Resource.Loading)
            val order = orderDao.getOrdersBetweenDates(startDate = startDate, endDate = endDate)
            order.collect { list->
                emit(Resource.Success(list.map { it.toDto() }))
            }
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }

    fun getLastDaysPrices(startDate: Long): Flow<Resource<List<Double>>> = flow {
        try {
            emit(Resource.Loading)
            val order = orderDao.getLastDaysPrices(startDate = startDate)
            order.collect { list->
                emit(Resource.Success(list))
            }
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }

    fun getPricesForDateRange(startDate: Long, endDate: Long): Flow<Resource<List<Double>>> = flow {
        try {
            emit(Resource.Loading)
            val order = orderDao.getPricesForDateRange(startDate = startDate, endDate = endDate)
            order.collect { list ->
                emit(Resource.Success(list))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }


    fun getTodaySales(): Flow<Resource<Double>> = flow {
        try {
            emit(Resource.Loading)
            val order = orderDao.getTodaySales() ?: 0.0
            emit(Resource.Success(order))
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }
}