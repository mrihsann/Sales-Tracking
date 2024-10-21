package com.ihsanarslan.salestracking.di

import android.content.Context
import androidx.room.Room
import com.ihsanarslan.salestracking.data.local.database.ProductDatabase
import com.ihsanarslan.salestracking.data.local.database.dao.ProductsDao
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
    fun provideDatabase(@ApplicationContext appContext: Context): ProductDatabase {
        return Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            "productdatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTytExamDao(database: ProductDatabase): ProductsDao {
        return database.productsDao()
    }



}