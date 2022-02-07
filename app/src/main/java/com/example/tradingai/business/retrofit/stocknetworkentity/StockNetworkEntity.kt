package com.example.tradingai.business.retrofit.stocknetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StockNetworkEntity(
    @Expose
    @SerializedName("1. symbol")
    val symbol : String,
    @Expose
    @SerializedName("2. name")
    val name : String,
    @Expose
    @SerializedName("3. type")
    val type : String,
    @Expose
    @SerializedName("4. region")
    val region : String,
    @Expose
    @SerializedName("5. marketOpen")
    val marketOpen : String,
    @Expose
    @SerializedName("6. marketClose")
    val marketClose : String,
    @Expose
    @SerializedName("7. timezone")
    val timezone : String,
    @Expose
    @SerializedName("8. currency")
    val currency: String,
    @Expose
    @SerializedName("9. matchScore")
    val matchScore: String
)