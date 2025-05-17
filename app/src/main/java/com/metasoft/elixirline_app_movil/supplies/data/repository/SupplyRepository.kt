package com.metasoft.elixirline_app_movil.supplies.data.repository

import android.util.Log
import com.metasoft.elixirline_app_movil.supplies.data.model.SupplyMapper
import com.metasoft.elixirline_app_movil.supplies.data.model.SupplyResponse
import com.metasoft.elixirline_app_movil.supplies.data.model.SupplyUsageResponse
import com.metasoft.elixirline_app_movil.supplies.data.remote.SupplyService
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.domain.model.SupplyUsage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class SupplyRepository(private val supplyService: SupplyService) {

    // Para desarrollo, mantenemos una lista en memoria
    private val supplies = mutableListOf<Supply>()
    private val supplyUsages = mutableListOf<SupplyUsage>()

    // Inicializar con datos de ejemplo
    init {
        // Agregar algunos insumos de ejemplo
        supplies.add(
            Supply(
                id = UUID.randomUUID(),
                name = "Fosfato Monopotásico",
                category = "Fertilizante",
                quantity = 25f,
                unit = "kg",
                location = "Bodega Principal",
                expirationDate = "12/05/2025",
                status = "Disponible"
            )
        )
        supplies.add(
            Supply(
                id = UUID.randomUUID(),
                name = "Herbicida A",
                category = "Pesticida",
                quantity = 8f,
                unit = "lt",
                location = "Bodega Principal",
                expirationDate = "12/05/2025",
                status = "Disponible"
            )
        )
        supplies.add(
            Supply(
                id = UUID.randomUUID(),
                name = "Guante de Trabajo",
                category = "Herramientas",
                quantity = 10f,
                unit = "Pares",
                location = "Almacén",
                expirationDate = "12/05/2025",
                status = "Disponible"
            )
        )
    }

    suspend fun getAllSupplies(): List<Supply> = withContext(Dispatchers.IO) {
        try {
            // En un entorno real, descomentar para usar la API
            /*
            val response = supplyService.getAllSupplies()
            if (response.isSuccessful) {
                return@withContext response.body()?.map {
                    SupplyMapper.toSupply(it)
                } ?: emptyList()
            }
            */

            // Para desarrollo, devolvemos la lista en memoria
            return@withContext supplies
        } catch (e: Exception) {
            Log.e("SupplyRepository", "Error al obtener insumos: ${e.message}", e)
            return@withContext emptyList()
        }
    }

    suspend fun getSupplyById(id: UUID): Supply? = withContext(Dispatchers.IO) {
        try {
            // Para desarrollo, buscamos en la lista en memoria
            return@withContext supplies.find { it.id == id }
        } catch (e: Exception) {
            Log.e("SupplyRepository", "Error al obtener insumo: ${e.message}", e)
            return@withContext null
        }
    }

    suspend fun createSupply(supply: Supply): Boolean = withContext(Dispatchers.IO) {
        try {
            // Para desarrollo, agregamos a la lista en memoria
            supplies.add(supply)
            return@withContext true
        } catch (e: Exception) {
            Log.e("SupplyRepository", "Error al crear insumo: ${e.message}", e)
            return@withContext false
        }
    }

    suspend fun updateSupply(supply: Supply): Boolean = withContext(Dispatchers.IO) {
        try {
            // Para desarrollo, actualizamos en la lista en memoria
            val index = supplies.indexOfFirst { it.id == supply.id }
            if (index != -1) {
                supplies[index] = supply
                return@withContext true
            }
            return@withContext false
        } catch (e: Exception) {
            Log.e("SupplyRepository", "Error al actualizar insumo: ${e.message}", e)
            return@withContext false
        }
    }

    suspend fun deleteSupply(id: UUID): Boolean = withContext(Dispatchers.IO) {
        try {
            // Para desarrollo, eliminamos de la lista en memoria
            val removed = supplies.removeIf { it.id == id }
            return@withContext removed
        } catch (e: Exception) {
            Log.e("SupplyRepository", "Error al eliminar insumo: ${e.message}", e)
            return@withContext false
        }
    }

    suspend fun getAllSupplyUsages(): List<SupplyUsage> = withContext(Dispatchers.IO) {
        try {
            // Para desarrollo, devolvemos la lista en memoria
            return@withContext supplyUsages
        } catch (e: Exception) {
            Log.e("SupplyRepository", "Error al obtener usos de insumos: ${e.message}", e)
            return@withContext emptyList()
        }
    }

    suspend fun registerSupplyUsage(supplyUsage: SupplyUsage): Boolean = withContext(Dispatchers.IO) {
        try {
            // Para desarrollo, agregamos a la lista en memoria y actualizamos la cantidad del insumo
            supplyUsages.add(supplyUsage)

            // Actualizar la cantidad del insumo
            val supplyIndex = supplies.indexOfFirst { it.id == supplyUsage.supplyId }
            if (supplyIndex != -1) {
                val supply = supplies[supplyIndex]
                val newQuantity = supply.quantity - supplyUsage.quantity
                if (newQuantity >= 0) {
                    supplies[supplyIndex] = supply.copy(quantity = newQuantity)
                    return@withContext true
                }
            }
            return@withContext false
        } catch (e: Exception) {
            Log.e("SupplyRepository", "Error al registrar uso de insumo: ${e.message}", e)
            return@withContext false
        }
    }
}
