package com.ihsanarslan.salestracking.domain.model

import android.net.Uri
import com.ihsanarslan.salestracking.data.entity.ProductsEntity

data class ProductsDto(
    val name : String,
    val description : String,
    val price : Double
)

fun ProductsDto.toEntity(): ProductsEntity {
    return ProductsEntity(
        name=name,
        description=description,
        price=price
    )
}