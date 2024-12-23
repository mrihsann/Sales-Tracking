package com.ihsanarslan.salestracking.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ihsanarslan.salestracking.data.entity.OrderEntity
import com.ihsanarslan.salestracking.data.entity.OrderProductCrossRefEntity
import com.ihsanarslan.salestracking.domain.model.OrderWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao{

    @Insert
    suspend fun insert(order: OrderEntity) : Long

    @Insert
    suspend fun insertOrderProductCrossRefs(crossRefs: List<OrderProductCrossRefEntity>)

    @Transaction
    suspend fun addOrderWithProducts(order: OrderEntity, products: Map<Int, Int>) {
        val orderId = insert(order)
        val crossRefs = products.map { (productId, quantity) ->
            OrderProductCrossRefEntity(
                orderId = orderId.toInt(),
                productId = productId,
                quantity = quantity
            )
        }
        insertOrderProductCrossRefs(crossRefs)
    }

    @Query("""
        UPDATE Orders 
        SET name = :name, 
            description = :description, 
            price = :price 
        WHERE orderId = :orderId
    """)
    suspend fun update(orderId: Int, name: String, description: String, price: Double)

    @Transaction
    suspend fun updateOrderWithProducts(order: OrderEntity, products: Map<Int, Int>) {
        // Siparişi güncelle
        update(
            orderId = order.orderId,
            name = order.name,
            description = order.description,
            price = order.price
        )

        // Önceki siparişle ilişkili ürünleri sil
        deleteOrderProducts(order.orderId)

        // Yeni ürünleri ekle
        val crossRefs = products.map { (productId, quantity) ->
            OrderProductCrossRefEntity(
                orderId = order.orderId,
                productId = productId,
                quantity = quantity
            )
        }
        insertOrderProductCrossRefs(crossRefs)
    }

    @Query("DELETE FROM OrderProductCrossRef WHERE orderId = :id")
    suspend fun deleteOrderProducts(id: Int)

    @Query("DELETE FROM Orders WHERE orderId = :id")
    suspend fun delete(id:Int)

    @Transaction
    suspend fun deleteOrderWithProducts(id: Int) {
        // Siparişle ilişkili ürünleri sil
        deleteOrderProducts(id)

        // Ardından siparişi sil
        delete(id)
    }

    @Transaction
    @Query(" SELECT *  FROM Orders ORDER BY createdAt DESC ")
    fun getOrdersWithTheOrdersProducts(): Flow<List<OrderWithProducts>>


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
        SELECT SUM(price) AS totalPrice
        FROM Orders
        WHERE createdAt BETWEEN :startDate AND :endDate
        GROUP BY createdAt / 86400000
        ORDER BY createdAt ASC
    """)
    fun getPricesForDateRange(startDate: Long, endDate: Long): Flow<List<Double>>


    @Query("""
        SELECT SUM(price) 
        FROM Orders 
        WHERE createdAt >= (
            (strftime('%s', 'now', 'localtime') - (strftime('%H', 'now', 'localtime') * 3600 + strftime('%M', 'now', 'localtime') * 60 + strftime('%S', 'now', 'localtime')))
            + (8 * 3600)
            + CASE WHEN strftime('%H', 'now', 'localtime') < 8 THEN -86400 ELSE 0 END
        ) * 1000
    """)
    suspend fun getTodaySales(): Double?
}