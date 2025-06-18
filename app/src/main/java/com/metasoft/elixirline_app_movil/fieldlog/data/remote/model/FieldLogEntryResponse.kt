package com.metasoft.elixirline_app_movil.fieldlog.data.remote.model

data class FieldLogEntryResponse(
    val entryId: String,
    val authorId: String,
    val parcelId: String,
    val description: String,
    val entryType: String,
    val timestamp: String,
    val photoUrls: List<String>
)
