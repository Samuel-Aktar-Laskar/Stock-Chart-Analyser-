package com.example.tradingai.model



data class Stock(
 val symbol : String,
 val name : String,
 val type : String,
 val region : String,
 val marketOpen : String,
 val marketClose : String,
 val timezone : String,
 val currency: String,
 val matchScore: String
)