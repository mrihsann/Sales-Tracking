package com.ihsanarslan.salestracking.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ihsanarslan.salestracking.data.entity.OrderEntity
import com.ihsanarslan.salestracking.data.entity.OrderProductCrossRefEntity
import com.ihsanarslan.salestracking.data.entity.ProductEntity

data class OrderWithProducts(
    @Embedded
    val order: OrderEntity,
    @Relation(
        /* Describes the Objects that make up the list (entity parameter can be omitted if it is implied)*/
        entity = ProductEntity::class,
        /* The field/column in the Embedded object that is referenced*/
        parentColumn = "orderId",
        /* The field/column in the referenced object(s) */
        entityColumn = "productId",
        /* Describes the mapping/association/relationship/reference .... */
        associateBy = Junction(
            /* The @Entity annotated Object that defines the table */
            value = OrderProductCrossRefEntity::class,
            /* the columns IN THE RELATION TABLE that reference the rows in the parent/child tables*/
            parentColumn = "orderId",
            entityColumn = "productId"
        )
    )
    val products: List<ProductEntity>,

    /*!!!!!!!!!! Added to get the mapped quantity !!!!!!!!!!*/
    @Relation(
        entity = OrderProductCrossRefEntity::class,
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val mappedQuantity: List<OrderProductCrossRefEntity>
)