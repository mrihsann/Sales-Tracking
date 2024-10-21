package com.ihsanarslan.salestracking.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ihsanarslan.salestracking.data.entity.ProductsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao{

    @Insert
    suspend fun insert(product: ProductsEntity)

    @Query("DELETE FROM Products WHERE id = :id")
    suspend fun delete(id:Int)

    @Update
    suspend fun update(product: ProductsEntity)

    @Query("SELECT * FROM Products ORDER BY id DESC")
    fun getAll() : Flow<List<ProductsEntity>>
}