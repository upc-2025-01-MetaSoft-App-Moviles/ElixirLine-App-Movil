package com.metasoft.elixirline_app_movil.data.model

import java.util.UUID

data class ProductionHistoryResponse(
    val recordId: UUID?,
    val batchId: UUID?,
    val startDate: String?,
    val endDate: String?,
    val volumeProduced: Float?,
    val qualityMetrics: Map<String, Float>?
)