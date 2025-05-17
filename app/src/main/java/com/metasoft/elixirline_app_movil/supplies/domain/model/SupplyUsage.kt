package com.metasoft.elixirline_app_movil.supplies.domain.model

import java.util.UUID

data class SupplyUsage(
    val id: UUID,
    val supplyId: UUID,
    val batchId: UUID,
    val quantity: Float,
    val activity: String,
    val date: String,
    val operatorName: String
)
