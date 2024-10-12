package com.ihsanarslan.salestracking.domain.model

import android.net.Uri

data class Product(
    val name : String,
    val description : String,
    val price : Double,
    val image : Uri
)
