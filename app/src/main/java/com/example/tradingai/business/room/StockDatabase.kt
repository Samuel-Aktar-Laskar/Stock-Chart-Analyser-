package com.example.tradingai.business.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [StockCacheEntity::class], version = 1)
abstract class StockDatabase : RoomDatabase(){

    abstract fun stockDao():StockDao

    companion object {
        val DATABASE_NAME: String = "Aktar"
    }
}