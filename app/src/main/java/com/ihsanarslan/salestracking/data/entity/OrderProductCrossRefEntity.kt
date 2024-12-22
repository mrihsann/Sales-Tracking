package com.ihsanarslan.salestracking.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["orderId", "productId"],
    tableName = "OrderProductCrossRef",
    foreignKeys = [
        ForeignKey(entity = OrderEntity::class, parentColumns = ["orderId"], childColumns = ["orderId"]),
        ForeignKey(entity = ProductEntity::class, parentColumns = ["productId"], childColumns = ["productId"])
    ],
    indices = [Index(value = ["orderId"]), Index(value = ["productId"])]
)
data class OrderProductCrossRefEntity(
    @ColumnInfo("orderId") val orderId: Int,
    @ColumnInfo("productId") val productId: Int,
    @ColumnInfo("quantity")val quantity: Int
)