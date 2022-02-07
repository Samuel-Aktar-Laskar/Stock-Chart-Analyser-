package com.example.tradingai.repository

import android.util.Log
import com.example.tradingai.model.Stock
import com.example.tradingai.model.StockEndpoint
import com.example.tradingai.model.Watchlist
import com.example.tradingai.retrofit.EndpointNetworkMapper
import com.example.tradingai.retrofit.NetworkMapper
import com.example.tradingai.retrofit.StockRetrofit
import com.example.tradingai.room.CacheMapper
import com.example.tradingai.room.StockCacheEntity
import com.example.tradingai.room.StockDao
import com.example.tradingai.util.DataState
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
            val endpointResp = stockRetrofit.GetStockEndpoint(
                symbol = symbol
            )
            if (endpointResp.isSuccessful){
                val endpoint = endpointResp.body() ?: return null
                return endpointNetworkMapper.mapFromEntity(endpoint.GlobalQuote)
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
            val watchlists = stocks.map {
                val endpoint = getStockEndpoint(it.symbol)
                Log.d(TAG, "getWatchList: Endpoint price :${endpoint?.Price}")
                Watchlist(
                    stock = it,
                    stockEndpoint = endpoint
                )
            }
            emit(DataState.Success(watchlists))
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