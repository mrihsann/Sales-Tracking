package com.ihsanarslan.salestracking.domain.repository

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

    suspend fun insert(order: OrderDto) : Resource<Unit> {
        return try {
            orderDao.insert(order.toEntity())
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    suspend fun delete(id: Int) : Resource<Unit> {
        return try {
            orderDao.delete(id)
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    suspend fun update(order: OrderDto) : Resource<Unit> {
        return try {
            orderDao.update(order.toEntity())
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    fun getAll(): Flow<Resource<List<OrderDto>>> = flow {
        emit(Resource.Loading)
        try {
            val order = orderDao.getAll()
            order.collect { list->
                emit(Resource.Success(list.map { it.toDto() }))
            }
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }

    fun getOrdersBetweenDates(startDate: Long, endDate: Long): Flow<Resource<List<OrderDto>>> = flow {
        emit(Resource.Loading)
        try {
            val order = orderDao.getOrdersBetweenDates(startDate = startDate, endDate = endDate)
            order.collect { list->
                emit(Resource.Success(list.map { it.toDto() }))
            }
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }
}