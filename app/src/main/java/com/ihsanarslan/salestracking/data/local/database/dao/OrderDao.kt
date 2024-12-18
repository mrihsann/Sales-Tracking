package com.ihsanarslan.salestracking.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ihsanarslan.salestracking.data.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao{

    @Insert
    suspend fun insert(order: OrderEntity)

    @Query("DELETE FROM Orders WHERE id = :id")
    suspend fun delete(id:Int)

    @Update
    suspend fun update(order: OrderEntity)

    @Query("SELECT * FROM Orders ORDER BY id DESC")
    fun getAll() : Flow<List<OrderEntity>>

    // Belirli bir tarih aralığındaki siparişleri getiren sorgu
    @Query("SELECT * FROM Orders WHERE createdAt BETWEEN :startDate AND :endDate ORDER BY createdAt DESC")
    fun getOrdersBetweenDates(startDate: Long, endDate: Long): Flow<List<OrderEntity>>
}