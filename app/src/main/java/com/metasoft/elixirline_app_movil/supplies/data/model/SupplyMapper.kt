package com.metasoft.elixirline_app_movil.supplies.data.model

import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.domain.model.SupplyUsage
import java.util.UUID

object SupplyMapper {
    fun toSupply(supplyResponse: SupplyResponse): Supply {
        return Supply(
            id = supplyResponse.id ?: UUID.randomUUID(),
            name = supplyResponse.name ?: "",
            category = supplyResponse.category ?: "",
            quantity = supplyResponse.quantity ?: 0f,
            unit = supplyResponse.unit ?: "",
            location = supplyResponse.location ?: "",
            expirationDate = supplyResponse.expirationDate ?: "",
            status = supplyResponse.status ?: "Disponible"
        )
    }

    fun toSupplyUsage(supplyUsageResponse: SupplyUsageResponse): SupplyUsage {
        return SupplyUsage(
            id = supplyUsageResponse.id ?: UUID.randomUUID(),
            supplyId = supplyUsageResponse.supplyId ?: UUID.randomUUID(),
            batchId = supplyUsageResponse.batchId ?: UUID.randomUUID(),
            quantity = supplyUsageResponse.quantity ?: 0f,
            activity = supplyUsageResponse.activity ?: "",
            date = supplyUsageResponse.date ?: "",
            operatorName = supplyUsageResponse.operatorName ?: ""
        )
    }
}
