package com.example.tradingai.business.retrofit.stocknetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StocksNetworkEntity(

    @SerializedName("bestMatches")
    @Expose
    var stocks: List<StockNetworkEntity> = listOf()
)

{
}