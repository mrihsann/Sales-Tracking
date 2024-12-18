package com.ihsanarslan.salestracking.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ihsanarslan.salestracking.data.entity.OrderEntity
import com.ihsanarslan.salestracking.data.local.database.dao.OrderDao

@Database(entities = [OrderEntity::class], version = 1, exportSchema = false)
abstract class STDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}