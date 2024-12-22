package com.ihsanarslan.salestracking.domain.model

import com.ihsanarslan.salestracking.data.entity.ProductEntity

data class ProductDto(
    val productId : Int = 0,
    val name : String,
    val description : String,
    val price : Double,
    val createdAt : Long?=null
)

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        productId = productId,
        name=name,
        description=description,
        price=price,
    )
}