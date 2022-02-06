package com.example.tradingai.retrofit

import com.example.tradingai.model.Stock
import com.example.tradingai.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<StockNetworkEntity, Stock>
{
    override fun mapToEntity(domainModel: Stock): StockNetworkEntity {
        return StockNetworkEntity(
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

    override fun mapFromEntity(entity: StockNetworkEntity): Stock {
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

    fun mapToEntityList(domainModels: List<Stock>) : StocksNetworkEntity {
        return StocksNetworkEntity(
            stocks = domainModels.map {
                mapToEntity(it)
            }
        )
    }

    fun mapFromEntityList(entities : StocksNetworkEntity): List<Stock>{
        return entities.stocks.map {
            mapFromEntity(it)
        }
    }
}