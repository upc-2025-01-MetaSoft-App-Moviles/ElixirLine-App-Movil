package com.metasoft.elixirline_app_movil.ProductionHistory.data.repository

import android.util.Log
import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.ProductionHistoryMapper
import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.ProductionRecordRequest
import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.UpdateVolumeRequest
import com.metasoft.elixirline_app_movil.ProductionHistory.data.remote.ProductionHistoryService
import com.metasoft.elixirline_app_movil.ProductionHistory.domain.model.ProductionHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class ProductionRepository(val productionHistoryService: ProductionHistoryService) {

    /*suspend fun findAllProductionHistory(recordId: UUID): List<ProductionHistory> =
        withContext(Dispatchers.IO) {
            try {
                val response = productionHistoryService.findAllProductionHistory()
                if (response.isSuccessful) {
                    return@withContext response.body()?.map {
                        ProductionHistoryMapper.toProductionHistory(it)
                    } ?: emptyList()
                } else {
                    Log.e("ProductionRepository", "Error: ${response.code()} - ${response.message()}")
                    return@withContext emptyList()
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext emptyList()
            }
        }*/

    // Obtener todos los registros
    suspend fun getAllProductionRecords(): List<ProductionHistory> =
        withContext(Dispatchers.IO) {
            try {
                val response = productionHistoryService.getAllProductionRecords()
                if (response.isSuccessful) {
                    return@withContext response.body()?.map {
                        ProductionHistoryMapper.toProductionHistory(it)
                    } ?: emptyList()
                } else {
                    Log.e("ProductionRepository", "Error: ${response.code()} - ${response.message()}")
                    return@withContext emptyList()
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext emptyList()
            }
        }

    // Obtener un registro por ID
    suspend fun getProductionRecord(recordId: UUID): ProductionHistory? =
        withContext(Dispatchers.IO) {
            try {
                val response = productionHistoryService.getProductionRecord(recordId)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext ProductionHistoryMapper.toProductionHistory(response.body()!!)
                } else {
                    Log.e("ProductionRepository", "Error: ${response.code()} - ${response.message()}")
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext null
            }
        }

    // Obtener registros por ID de lote
    suspend fun getProductionRecordsByBatch(batchId: UUID): List<ProductionHistory> =
        withContext(Dispatchers.IO) {
            try {
                val response = productionHistoryService.getProductionRecordsByBatch(batchId)
                if (response.isSuccessful) {
                    return@withContext response.body()?.map {
                        ProductionHistoryMapper.toProductionHistory(it)
                    } ?: emptyList()
                } else {
                    Log.e("ProductionRepository", "Error: ${response.code()} - ${response.message()}")
                    return@withContext emptyList()
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext emptyList()
            }
        }

    suspend fun createProductionRecord(
        batchId: UUID,
        startDate: String,
        endDate: String,
        volumeProduced: Float,
        brix: Float,
        ph: Float,
        temperature: Float
    ): ProductionHistory? =
        withContext(Dispatchers.IO) {
            try {
                val request = ProductionRecordRequest(
                    batchId = batchId,
                    startDate = startDate,
                    endDate = endDate,
                    volumeProduced = volumeProduced,
                    brix = brix,
                    ph = ph,
                    temperature = temperature
                )

                val response = productionHistoryService.createProductionRecord(request)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext ProductionHistoryMapper.toProductionHistory(response.body()!!)
                } else {
                    Log.e("ProductionRepository", "Error: ${response.code()} - ${response.message()}")
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext null
            }
        }

    // Actualizar volumen de producción
    suspend fun updateProductionVolume(recordId: UUID, volumeProduced: Float): ProductionHistory? =
        withContext(Dispatchers.IO) {
            try {
                val request = UpdateVolumeRequest(volumeProduced = volumeProduced)

                val response = productionHistoryService.updateProductionVolume(recordId, request)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext ProductionHistoryMapper.toProductionHistory(response.body()!!)
                } else {
                    Log.e("ProductionRepository", "Error: ${response.code()} - ${response.message()}")
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext null
            }
        }

    // Eliminar un registro
    suspend fun deleteProductionRecord(recordId: UUID): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val response = productionHistoryService.deleteProductionRecord(recordId)
                if (response.isSuccessful) {
                    return@withContext true
                } else {
                    Log.e("ProductionRepository", "Error: ${response.code()} - ${response.message()}")
                    return@withContext false
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext false
            }
        }
}