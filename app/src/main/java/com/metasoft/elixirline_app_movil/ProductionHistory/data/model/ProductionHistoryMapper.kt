package com.metasoft.elixirline_app_movil.ProductionHistory.data.model

import com.metasoft.elixirline_app_movil.ProductionHistory.domain.model.ProductionHistory
import com.metasoft.elixirline_app_movil.ProductionHistory.domain.model.QualityMetrics
import java.util.UUID

object ProductionHistoryMapper {
    fun toProductionHistory(productionHistoryResponse: ProductionHistoryResponse): ProductionHistory {
        // Crear objeto QualityMetrics a partir de los campos individuales
        val qualityMetrics = QualityMetrics(
            Brix = productionHistoryResponse.brix ?: 0f,
            Ph = productionHistoryResponse.ph ?: 0f,
            Temperature = productionHistoryResponse.temperature ?: 0f
        )

        return ProductionHistory(
            recordId = productionHistoryResponse.recordId ?: UUID.fromString("00000000-0000-0000-0000-000000000000"),
            batchId = productionHistoryResponse.batchId ?: UUID.fromString("00000000-0000-0000-0000-000000000000"),
            startDate = productionHistoryResponse.startDate ?: "",
            endDate = productionHistoryResponse.endDate ?: "",
            volumeProduced = productionHistoryResponse.volumeProduced ?: 0f,
            qualityMetrics = qualityMetrics
        )
    }
}