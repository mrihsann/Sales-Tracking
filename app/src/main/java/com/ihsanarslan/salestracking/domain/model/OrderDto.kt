package com.ihsanarslan.salestracking.domain.model

import com.ihsanarslan.salestracking.data.entity.OrderEntity

data class OrderDto(
    val id : Int = 0,
    val name : String,
    val description : String,
    val price : Double,
    val createdAt : Long?=null
)

fun OrderDto.toEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        name=name,
        description=description,
        price=price,
    )
}