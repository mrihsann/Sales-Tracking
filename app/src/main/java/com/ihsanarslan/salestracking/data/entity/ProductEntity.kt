package com.ihsanarslan.salestracking.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.model.ProductDto

@Entity(tableName = "Products")
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    val id:Int=0,

    @ColumnInfo("name")
    val name:String,

    @ColumnInfo("description")
    val description:String,

    @ColumnInfo("price")
    val price:Double,

    @ColumnInfo("createdAt")
    val createdAt: Long = System.currentTimeMillis()
)

fun ProductEntity.toDto(): ProductDto {
    return ProductDto(
        id=id,
        name=name,
        description=description,
        price=price,
        createdAt = createdAt
    )
}