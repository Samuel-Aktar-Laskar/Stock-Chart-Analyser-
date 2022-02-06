package com.example.tradingai.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertStock(stockCacheEntity: StockCacheEntity): Long

    @Query("SELECT * FROM WatchListStocks")
    suspend fun GetWatchlistStoks():List<StockCacheEntity>

}