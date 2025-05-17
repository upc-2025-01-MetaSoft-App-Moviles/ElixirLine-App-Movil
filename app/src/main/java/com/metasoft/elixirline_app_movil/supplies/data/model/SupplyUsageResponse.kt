package com.metasoft.elixirline_app_movil.supplies.data.model

import java.util.UUID

data class SupplyUsageResponse(
    val id: UUID?,
    val supplyId: UUID?,
    val batchId: UUID?,
    val quantity: Float?,
    val activity: String?,
    val date: String?,
    val operatorName: String?
)
