package com.example.tradingai.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StocksNetworkEntity(

    @SerializedName("bestMatches")
    @Expose
    var stocks: List<StockNetworkEntity> = listOf()
)

{
}