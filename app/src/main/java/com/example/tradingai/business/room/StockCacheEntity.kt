package com.example.tradingai.business.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "WatchListStocks")
class StockCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "StockSymbol")
    val symbol : String,
    @ColumnInfo(name = "StockName")
    val name : String,
    @ColumnInfo(name = "StockType")
    val type : String,
    @ColumnInfo(name = "StockRegion")
    val region : String,
    @ColumnInfo(name = "MarketOpen")
    val marketOpen : String,
    @ColumnInfo(name = "MarketClose")
    val marketClose : String,
    @ColumnInfo(name = "TimeZone")
    val timezone : String,
    @ColumnInfo(name = "Currency")
    val currency: String,
    @ColumnInfo(name = "MatchScore")
    val matchScore: String
)
