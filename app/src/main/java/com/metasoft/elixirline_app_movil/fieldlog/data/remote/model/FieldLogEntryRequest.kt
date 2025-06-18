package com.metasoft.elixirline_app_movil.fieldlog.data.remote.model

data class FieldLogEntryRequest(
    val authorId: String,
    val parcelId: String,
    val description: String,
    val entryType: String,
    val relatedTaskId: String,
    val photoUrls: List<String> = emptyList()
)
