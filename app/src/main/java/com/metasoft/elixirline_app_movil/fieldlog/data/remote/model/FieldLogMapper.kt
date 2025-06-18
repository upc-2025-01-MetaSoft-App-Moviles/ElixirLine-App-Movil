package com.metasoft.elixirline_app_movil.fieldlog.data.remote.model

import com.metasoft.elixirline_app_movil.fieldlog.domain.model.FieldLogEntry

object FieldLogMapper {
    fun toDomain(response: FieldLogEntryResponse): FieldLogEntry {
        return FieldLogEntry(
            entryId = response.entryId,
            authorId = response.authorId,
            parcelId = response.parcelId,
            description = response.description,
            entryType = response.entryType,
            timestamp = response.timestamp,
            photoUrls = response.photoUrls
        )
    }
}
