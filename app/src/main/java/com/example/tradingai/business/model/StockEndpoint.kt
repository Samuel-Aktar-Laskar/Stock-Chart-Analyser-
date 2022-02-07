package com.example.tradingai.business.model

data class StockEndpoint(
    val Symbol : String,
    val Price : String,
    val Change : String,
    val ChangePercentage: String
)
