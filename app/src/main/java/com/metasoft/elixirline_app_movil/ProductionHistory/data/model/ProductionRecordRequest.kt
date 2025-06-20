package com.metasoft.elixirline_app_movil.ProductionHistory.data.model

import java.util.UUID

data class ProductionRecordRequest(
    val batchId: UUID,
    val startDate: String,
    val endDate: String,
    val volumeProduced: Float,
    val brix: Float,
    val ph: Float,
    val temperature: Float
)

data class UpdateVolumeRequest(
    val volumeProduced: Float
)