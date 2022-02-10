package com.example.tradingai.business.retrofit

import com.example.tradingai.apiKey
import com.example.tradingai.business.retrofit.stockendpointnetworkentity.StockEndpointsNetworkEntity
import com.example.tradingai.business.retrofit.stocknetworkentity.StocksNetworkEntity
import retrofit2.Call
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

    @GET("/query")
    suspend fun GetStockEndpoint(
        @Query("function") function : String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = apiKey
    ) : Response<StockEndpointsNetworkEntity>


    @GET("/query")
    suspend fun GetIntradayTimeStamp(
        @Query("function") function : String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = apiKey,
        @Query("interval") interval : String = "5min"
    )
}