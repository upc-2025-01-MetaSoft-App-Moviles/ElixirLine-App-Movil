package com.metasoft.elixirline_app_movil.fieldlog.data.repository

import android.util.Log
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.FieldLogService
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.model.FieldLogEntryRequest
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.model.FieldLogMapper
import com.metasoft.elixirline_app_movil.fieldlog.domain.model.FieldLogEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FieldLogRepository(private val service: FieldLogService) {

    suspend fun getAllEntries(): List<FieldLogEntry> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = service.getFieldLogEntries()
            if (response.isSuccessful) {
                response.body()?.map { FieldLogMapper.toDomain(it) } ?: emptyList()
            } else {
                Log.e("FieldLogRepository", "Error al obtener: ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("FieldLogRepository", "Excepción: ${e.message}")
            emptyList()
        }
    }

    suspend fun createEntry(request: FieldLogEntryRequest): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = service.createFieldLogEntry(request)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("FieldLogRepository", "Error al enviar bitácora: ${e.message}")
            false
        }
    }
}
