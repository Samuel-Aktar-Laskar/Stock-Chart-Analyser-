package com.example.tradingai.retrofit.stockendpointnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StockEndpointsNetworkEntity (
    @Expose
    @SerializedName("Global Quote")
    val GlobalQuote : StockEndpointNetworkEntity
        ){
}