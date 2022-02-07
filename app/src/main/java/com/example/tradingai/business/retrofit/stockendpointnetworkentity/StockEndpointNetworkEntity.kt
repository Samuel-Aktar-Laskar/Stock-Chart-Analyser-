package com.example.tradingai.business.retrofit.stockendpointnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StockEndpointNetworkEntity(
    @Expose
    @SerializedName("01. symbol")
    val StockSymbol : String,
    @Expose
    @SerializedName("02. open")
    val StockOpen : String,
    @Expose
    @SerializedName("03. high")
    val StockHigh : String,
    @Expose
    @SerializedName("04. low")
    val StockLow : String,
    @Expose
    @SerializedName("05. price")
    val StockPrice : String,
    @Expose
    @SerializedName("06. volume")
    val StockVolume : String,
    @Expose
    @SerializedName("07. latest trading day")
    val StockLastTradingDay : String,
    @Expose
    @SerializedName("08. previous close")
    val StockPreviousClose : String,
    @Expose
    @SerializedName("09. change")
    val StockValueChange : String,
    @Expose
    @SerializedName("10. change percent")
    val StockChangePercentage: String,
) {
}