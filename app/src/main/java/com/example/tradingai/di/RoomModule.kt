package com.example.tradingai.di

import android.content.Context
import androidx.room.Room
import com.example.tradingai.room.StockDao
import com.example.tradingai.room.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideStockDb(@ApplicationContext context: Context): StockDatabase {
        return Room
            .databaseBuilder(
                context,
                StockDatabase::class.java,
                StockDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDAO(stockDatabase: StockDatabase): StockDao {
        return stockDatabase.stockDao()
    }
}