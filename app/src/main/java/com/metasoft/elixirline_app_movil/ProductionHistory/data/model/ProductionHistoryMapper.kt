package com.metasoft.elixirline_app_movil.ProductionHistory.data.model

import com.metasoft.elixirline_app_movil.ProductionHistory.model.ProductionHistory
import java.util.UUID

object ProductionHistoryMapper {
    fun toProductionHistory(productionHistoryResponse: ProductionHistoryResponse): ProductionHistory {
        return ProductionHistory(
            recordId = productionHistoryResponse.recordId ?: UUID.fromString("00000000-0000-0000-0000-000000000000"),
            batchId = productionHistoryResponse.batchId ?: UUID.fromString("00000000-0000-0000-0000-000000000000"),
            startDate = productionHistoryResponse.startDate ?: "",
            endDate = productionHistoryResponse.endDate ?: "",
            volumeProduced = productionHistoryResponse.volumeProduced ?: 0f,
            qualityMetrics = productionHistoryResponse.qualityMetrics ?: emptyMap()
        )
    }
}