package com.example.tradingai.business.repository

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import com.example.tradingai.business.model.Stock
import com.example.tradingai.business.model.StockEndpoint
import com.example.tradingai.business.model.Watchlist
import com.example.tradingai.business.retrofit.EndpointNetworkMapper
import com.example.tradingai.business.retrofit.NetworkMapper
import com.example.tradingai.business.retrofit.StockRetrofit
import com.example.tradingai.business.room.CacheMapper
import com.example.tradingai.business.room.StockCacheEntity
import com.example.tradingai.business.room.StockDao
import com.example.tradingai.util.DataState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "MainRepositoryT"
class MainRepository
constructor(
    private val networkMapper: NetworkMapper,
    private val endpointNetworkMapper: EndpointNetworkMapper,
    private val stockRetrofit: StockRetrofit,
    private val cacheMapper: CacheMapper,
    private val stockDao: StockDao
){
   
    suspend fun getStockEndpoint(symbol : String) : StockEndpoint? {
        return try {
            Log.d(TAG, "getStockEndpoint: a")
            val endpointResp = stockRetrofit.GetStockEndpoint(
                symbol = symbol
            )
            Log.d(TAG, "getStockEndpoint: b")
            if (endpointResp.isSuccessful){
                Log.d(TAG, "getStockEndpoint: c")
                val endpoint = endpointResp.body() ?: return null
                Log.d(TAG, "getStockEndpoint: e")
                return endpointNetworkMapper.mapFromEntity(endpoint.GlobalQuote)
                Log.d(TAG, "getStockEndpoint: f")
            }
            return null
        }
        catch (e: Exception){
            Log.d(TAG, "getStockEndpoint: Error :${e.localizedMessage}")
            return null
        }
    }

    suspend fun getStocks(keyword: String): Flow<DataState<List<Stock>>> = flow {
        Log.d(TAG, "getStocks: CAlled getStocks")
        emit (DataState.Loading)
        try {

            val stocksResponse = stockRetrofit.GetSearchResponse(keyword = keyword)
            if (stocksResponse.isSuccessful){
                val stocksEntity = stocksResponse.body() ?: return@flow
                for (a in stocksEntity.stocks){
                    Log.d(TAG, "getStocks: ${a.symbol}")
                }
                emit(DataState.Success(networkMapper.mapFromEntityList(stocksEntity)))
            }
        }
        catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun getWatchList() : Flow<DataState<List<Watchlist>>> = flow {
        Log.d(TAG, "getWatchList: ")
        emit(DataState.Loading)
        try {
            val stockCacheEntities : List<StockCacheEntity> = stockDao.GetWatchlistStoks()
            val stocks = cacheMapper.mapFromEntityList(stockCacheEntities)

            val partialWatchlist = stocks.map {
                Watchlist(
                    stock = it,
                    stockEndpoint = null
                )
            } as ArrayList
            emit(DataState.PartialSuccess(partialWatchlist))

            for (i in 0 until partialWatchlist.size){
                Log.d(TAG, "getWatchList: before calling")
                val endpoint = getStockEndpoint(stocks[i].symbol)
                delay(500)
                Log.d(TAG, "getWatchList: after calling")
                partialWatchlist[i] = Watchlist(
                    stocks[i],
                    endpoint
                )
                if(i%3 ==0) emit(DataState.PartialSuccess(partialWatchlist as List<Watchlist>))
            }

            emit(DataState.Success(partialWatchlist as List<Watchlist>))

         /*   val watchlists = stocks.map {
                withContext(Dispatchers.IO){
                    val endpoint = getStockEndpoint(it.symbol)
                    Log.d(TAG, "getWatchList: Endpoint price :${endpoint?.Price}")
                    Watchlist(
                        stock = it,
                        stockEndpoint = endpoint
                    )
                }

            }*/

        }
        catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun addStockInWatchList(stock: Stock) : Flow<DataState<Long>> = flow {
        emit(DataState.Loading)
        try {
            val cacheStock = cacheMapper.mapToEntity(stock)
            val l =stockDao.InsertStock(cacheStock)
            emit(DataState.Success(l))
        }
        catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}