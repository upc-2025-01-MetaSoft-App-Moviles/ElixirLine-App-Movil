package com.metasoft.elixirline_app_movil.ProductionHistory.data.repository

import android.util.Log
import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.ProductionHistoryMapper
import com.metasoft.elixirline_app_movil.ProductionHistory.data.remote.ProductionHistoryService
import com.metasoft.elixirline_app_movil.ProductionHistory.model.ProductionHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class ProductionRepository(val productionHistoryService: ProductionHistoryService) {

    suspend fun findAllProductionHistory(recordId: UUID): List<ProductionHistory> =
        withContext(Dispatchers.IO) {
            try {
                val response = productionHistoryService.findAllProductionHistory()

                if (response.isSuccessful) {
                    Log.d("ProductionRepository", "Respuesta exitosa")
                    return@withContext response.body()?.map {
                        ProductionHistoryMapper.toProductionHistory(it)
                    } ?: emptyList()
                } else {
                    Log.e(
                        "ProductionRepository",
                        "Error: ${response.code()} - ${response.message()}"
                    )
                    return@withContext emptyList()
                }
            } catch (e: Exception) {
                Log.e("ProductionRepository", "Error en la conexión: ${e.message}", e)
                return@withContext emptyList()
            }
        }
}