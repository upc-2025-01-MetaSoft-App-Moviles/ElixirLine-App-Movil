package com.metasoft.elixirline_app_movil.ProductionHistory.domain.model

import java.util.UUID

data class ProductionHistory(
    val recordId: UUID,
    val batchId: UUID,
    val startDate: String,
    val endDate: String,
    val volumeProduced: Float,
    val qualityMetrics: QualityMetrics
)