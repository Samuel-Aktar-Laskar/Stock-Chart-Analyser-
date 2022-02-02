package com.example.tradingai.retrofitinterfaces

import com.example.tradingai.apiKey
import com.example.tradingai.dtomodels.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchResponceApi {
    @GET("/query")
    suspend fun GetSearchResponse(
        @Query("function") function : String="SYMBOL_SEARCH",
        @Query("keywords") keyword : String,
        @Query("apikey") apikey: String = apiKey
    ) : Response<SearchResponse>
}