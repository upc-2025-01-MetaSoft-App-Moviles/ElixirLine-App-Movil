package com.metasoft.elixirline_app_movil.fieldlog.domain.model

data class FieldLogEntry(
    val entryId: String,
    val authorId: String,
    val parcelId: String,
    val description: String,
    val entryType: String,
    val timestamp: String,
    val photoUrls: List<String>
)
