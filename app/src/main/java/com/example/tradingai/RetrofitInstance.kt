package com.example.tradingai

import com.example.tradingai.retrofitinterfaces.SearchResponceApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: SearchResponceApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.alphavantage.co")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchResponceApi::class.java)
    }
}