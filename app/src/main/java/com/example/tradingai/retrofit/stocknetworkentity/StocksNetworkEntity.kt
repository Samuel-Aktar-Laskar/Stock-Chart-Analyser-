package com.example.tradingai.retrofit.stocknetworkentity

import com.example.tradingai.retrofit.stocknetworkentity.StockNetworkEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StocksNetworkEntity(

    @SerializedName("bestMatches")
    @Expose
    var stocks: List<StockNetworkEntity> = listOf()
)

{
}