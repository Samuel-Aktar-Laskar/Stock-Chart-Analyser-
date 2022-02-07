package com.example.tradingai.business.retrofit.stockendpointnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StockEndpointsNetworkEntity (
    @Expose
    @SerializedName("Global Quote")
    val GlobalQuote : StockEndpointNetworkEntity
        ){
}