package com.ihsanarslan.salestracking.domain.repository

import com.ihsanarslan.salestracking.data.entity.toDto
import com.ihsanarslan.salestracking.data.local.database.dao.ProductsDao
import com.ihsanarslan.salestracking.domain.model.ProductsDto
import com.ihsanarslan.salestracking.domain.model.toEntity
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsDaoImpl @Inject constructor(
    private val productsDao: ProductsDao
){

    suspend fun insert(product: ProductsDto) : Resource<Unit> {
        return try {
            productsDao.insert(product.toEntity())
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    suspend fun delete(id: Int) : Resource<Unit> {
        return try {
            productsDao.delete(id)
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    suspend fun update(product: ProductsDto) : Resource<Unit> {
        return try {
            productsDao.update(product.toEntity())
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    fun getAll(): Flow<Resource<List<ProductsDto>>> = flow {
        emit(Resource.Loading)
        try {
            val products = productsDao.getAll()
            products.collect { list->
                emit(Resource.Success(list.map { it.toDto() }))
            }
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }
}