package com.ihsanarslan.salestracking.di

import android.content.Context
import androidx.room.Room
import com.ihsanarslan.salestracking.data.local.database.STDatabase
import com.ihsanarslan.salestracking.data.local.database.dao.OrderDao
import com.ihsanarslan.salestracking.data.local.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): STDatabase {
        return Room.databaseBuilder(
            appContext,
            STDatabase::class.java,
            "salestrackingdb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOrderDao(database: STDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: STDatabase): ProductDao {
        return database.productDao()
    }

}