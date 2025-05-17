package com.metasoft.elixirline_app_movil.supplies.data.model

import java.util.UUID

data class SupplyResponse(
    val id: UUID?,
    val name: String?,
    val category: String?,
    val quantity: Float?,
    val unit: String?,
    val location: String?,
    val expirationDate: String?,
    val status: String?
)
