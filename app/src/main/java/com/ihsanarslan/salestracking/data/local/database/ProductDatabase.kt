package com.ihsanarslan.salestracking.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ihsanarslan.salestracking.data.entity.ProductsEntity
import com.ihsanarslan.salestracking.data.local.database.dao.ProductsDao

@Database(entities = [ProductsEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}