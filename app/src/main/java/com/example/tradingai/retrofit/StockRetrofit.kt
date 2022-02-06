package com.example.tradingai.retrofit

import com.example.tradingai.apiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockRetrofit {
    @GET("/query")
    suspend fun GetSearchResponse(
        @Query("function") function : String="SYMBOL_SEARCH",
        @Query("keywords") keyword : String,
        @Query("apikey") apikey: String = apiKey
    ) : Response<StocksNetworkEntity>
}