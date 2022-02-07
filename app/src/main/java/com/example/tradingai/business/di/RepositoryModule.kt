package com.example.tradingai.business.di

import com.example.tradingai.business.repository.MainRepository
import com.example.tradingai.business.retrofit.EndpointNetworkMapper
import com.example.tradingai.business.retrofit.NetworkMapper
import com.example.tradingai.business.retrofit.StockRetrofit
import com.example.tradingai.business.room.CacheMapper
import com.example.tradingai.business.room.StockDao
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