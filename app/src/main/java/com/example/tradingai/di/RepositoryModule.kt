package com.example.tradingai.di

import com.example.tradingai.repository.MainRepository
import com.example.tradingai.retrofit.EndpointNetworkMapper
import com.example.tradingai.retrofit.NetworkMapper
import com.example.tradingai.retrofit.StockRetrofit
import com.example.tradingai.room.CacheMapper
import com.example.tradingai.room.StockDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
         networkMapper: NetworkMapper,
         endpointNetworkMapper: EndpointNetworkMapper,
         stockRetrofit: StockRetrofit,
         cacheMapper: CacheMapper,
         stockDao: StockDao
    ) : MainRepository {
        return MainRepository(
            networkMapper = networkMapper,
            stockRetrofit = stockRetrofit,
            cacheMapper = cacheMapper,
            stockDao = stockDao,
            endpointNetworkMapper = endpointNetworkMapper
        )
    }
}