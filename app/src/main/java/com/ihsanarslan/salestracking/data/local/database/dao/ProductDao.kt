package com.ihsanarslan.salestracking.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ihsanarslan.salestracking.data.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao{

    @Insert
    suspend fun insert(product: ProductEntity)

    @Query("DELETE FROM Products WHERE productId = :id")
    suspend fun delete(id:Int)

    @Query("""
        UPDATE Products 
        SET name = :name, 
            description = :description, 
            price = :price 
        WHERE productId = :productId
    """)
    suspend fun update(productId: Int, name: String, description: String, price: Double)

    @Query("SELECT * FROM Products ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ProductEntity>>
}