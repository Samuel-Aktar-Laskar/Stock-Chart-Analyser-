package com.example.tradingai.room

import com.example.tradingai.model.Stock
import com.example.tradingai.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : EntityMapper<StockCacheEntity, Stock> {
    override fun mapToEntity(domainModel: Stock): StockCacheEntity {
        return StockCacheEntity(
            domainModel.symbol,
            domainModel.name,
            domainModel.type,
            domainModel.region,
            domainModel.marketOpen,
            domainModel.marketClose,
            domainModel.timezone,
            domainModel.currency,
            domainModel.matchScore
        )
    }

    override fun mapFromEntity(entity: StockCacheEntity): Stock {
        return Stock(
            entity.symbol,
            entity.name,
            entity.type,
            entity.region,
            entity.marketOpen,
            entity.marketClose,
            entity.timezone,
            entity.currency,
            entity.matchScore
        )
    }

    fun mapToEntityList(domainModels: List<Stock>) : List<StockCacheEntity> {
        return domainModels.map {
            mapToEntity(it)
        }
    }

    fun mapFromEntityList(entities : List<StockCacheEntity>): List<Stock>{
        return entities.map {
            mapFromEntity(it)
        }
    }
}