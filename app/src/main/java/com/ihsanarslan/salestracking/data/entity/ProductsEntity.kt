package com.ihsanarslan.salestracking.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ihsanarslan.salestracking.domain.model.ProductsDto

@Entity(tableName = "Products")
data class ProductsEntity(

    @PrimaryKey(autoGenerate = true)
    val id:Int=0,

    @ColumnInfo("name")
    val name:String,

    @ColumnInfo("description")
    val description:String,

    @ColumnInfo("price")
    val price:Double,

)

fun ProductsEntity.toDto(): ProductsDto {
    return ProductsDto(
        name=name,
        description=description,
        price=price
    )
}