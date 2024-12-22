package com.ihsanarslan.salestracking.domain.repository

import com.ihsanarslan.salestracking.data.entity.ProductEntity
import com.ihsanarslan.salestracking.data.entity.toDto
import com.ihsanarslan.salestracking.data.local.database.dao.ProductDao
import com.ihsanarslan.salestracking.domain.model.ProductDto
import com.ihsanarslan.salestracking.domain.model.toEntity
import com.ihsanarslan.salestracking.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductDaoImpl @Inject constructor(
    private val productDao: ProductDao
){

    suspend fun insert(product: ProductDto) : Resource<Unit> {
        return try {
            Resource.Loading
            productDao.insert(product.toEntity())
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    suspend fun delete(id: Int) : Resource<Unit> {
        return try {
            Resource.Loading
            productDao.delete(id)
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    suspend fun update(product: ProductDto) : Resource<Unit> {
        return try {
            Resource.Loading
            productDao.update(
                productId = product.productId,
                name = product.name,
                description = product.description,
                price = product.price
            )
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e)
        }
    }

    fun getAll() : Flow<Resource<List<ProductDto>>> = flow {
        try {
            emit(Resource.Loading)
            val data = productDao.getAll()
            val resultFlow = data.map { entityList ->
                entityList.map { productEntity ->
                    productEntity.toDto()
                }
            }
            resultFlow.collect{
                emit(Resource.Success(it))
            }
        }
        catch (e: Exception){
            emit(Resource.Error(e))
        }
    }
}