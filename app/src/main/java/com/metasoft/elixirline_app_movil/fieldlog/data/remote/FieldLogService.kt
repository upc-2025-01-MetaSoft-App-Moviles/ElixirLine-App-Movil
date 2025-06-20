package com.metasoft.elixirline_app_movil.fieldlog.data.remote

import com.metasoft.elixirline_app_movil.fieldlog.data.remote.model.FieldLogEntryRequest
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.model.FieldLogEntryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FieldLogService {
    @GET("api/v1/field-log-entry")
    suspend fun getFieldLogEntries(): Response<List<FieldLogEntryResponse>>

    @POST("api/v1/field-log-entry")
    suspend fun createFieldLogEntry(@Body request: FieldLogEntryRequest): Response<FieldLogEntryResponse>
}
