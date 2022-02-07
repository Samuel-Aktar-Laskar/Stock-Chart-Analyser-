package com.example.tradingai.business.retrofit

import com.example.tradingai.business.model.StockEndpoint
import com.example.tradingai.business.retrofit.stockendpointnetworkentity.StockEndpointNetworkEntity
import com.example.tradingai.util.EntityMapper
import javax.inject.Inject

class EndpointNetworkMapper
    @Inject
    constructor()
    : EntityMapper<StockEndpointNetworkEntity, StockEndpoint> {
    override fun mapToEntity(domainModel: StockEndpoint): StockEndpointNetworkEntity {
        TODO("Not yet implemented")
    }

    override fun mapFromEntity(entity: StockEndpointNetworkEntity): StockEndpoint {
        return StockEndpoint(
            entity.StockSymbol,
            entity.StockPrice,
            entity.StockValueChange,
            entity.StockChangePercentage
        )
    }

}