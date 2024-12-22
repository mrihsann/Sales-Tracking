package com.ihsanarslan.salestracking.domain.model

import com.ihsanarslan.salestracking.data.entity.OrderEntity

data class OrderDto(
    val orderId : Int = 0,
    val name : String,
    val description : String,
    val price : Double,
    val createdAt : Long?=null
)

fun OrderDto.toEntity(): OrderEntity {
    return OrderEntity(
        orderId = orderId,
        name=name,
        description=description,
        price=price,
    )
}