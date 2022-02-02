package com.example.tradingai.dtomodels

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("bestMatches")
    val Responses : List<StocksSymbol> = listOf()
        )