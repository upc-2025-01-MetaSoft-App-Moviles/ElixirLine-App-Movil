package com.metasoft.elixirline_app_movil.supplies.domain.model

import java.util.UUID

data class Supply(
    val id: UUID,
    val name: String,
    val category: String,
    val quantity: Float,
    val unit: String,
    val location: String,
    val expirationDate: String,
    val status: String
)
