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

    @Query("""
        SELECT * 
        FROM Orders 
        ORDER BY createdAt DESC 
        LIMIT 10
    """)
    fun getLast10Orders(): Flow<List<OrderEntity>>

    // Belirli bir tarih aralığındaki siparişleri getiren sorgu
    @Query("SELECT * FROM Orders WHERE createdAt BETWEEN :startDate AND :endDate ORDER BY createdAt DESC")
    fun getOrdersBetweenDates(startDate: Long, endDate: Long): Flow<List<OrderEntity>>

    @Query("""
        SELECT SUM(price) AS totalPrice
        FROM Orders
        WHERE createdAt >= :startDate
        GROUP BY createdAt / 86400000
        ORDER BY createdAt ASC
    """)
    fun getLastDaysPrices(startDate: Long): Flow<List<Double>>

    @Query("""
        SELECT SUM(price) 
        FROM Orders 
        WHERE createdAt >= (
            (strftime('%s', 'now', 'localtime') - (strftime('%H', 'now', 'localtime') * 3600 + strftime('%M', 'now', 'localtime') * 60 + strftime('%S', 'now', 'localtime')))
            + (8 * 3600)
            + CASE WHEN strftime('%H', 'now', 'localtime') < 8 THEN -86400 ELSE 0 END
        ) * 1000 -- Milisaniye dönüşümü
    """)
    suspend fun getTodaySales(): Double?
}