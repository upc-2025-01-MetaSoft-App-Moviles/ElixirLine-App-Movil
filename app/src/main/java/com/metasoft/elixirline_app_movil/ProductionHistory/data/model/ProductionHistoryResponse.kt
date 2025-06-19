package com.metasoft.elixirline_app_movil.ProductionHistory.data.model

import com.metasoft.elixirline_app_movil.ProductionHistory.domain.model.QualityMetrics
import java.util.UUID

data class ProductionHistoryResponse(
    val recordId: UUID?,
    val batchId: UUID?,
    val startDate: String?,
    val endDate: String?,
    val volumeProduced: Float?,
    val brix: Float?,
    val ph: Float?,
    val temperature: Float?
)