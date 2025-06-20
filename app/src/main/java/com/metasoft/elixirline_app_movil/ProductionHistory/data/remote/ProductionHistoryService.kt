package com.metasoft.elixirline_app_movil.ProductionHistory.data.remote

import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.ProductionHistoryResponse
import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.ProductionRecordRequest
import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.UpdateVolumeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface ProductionHistoryService {
    @GET("id")
    suspend fun findAllProductionHistory(): Response<List<ProductionHistoryResponse>>

    // Obtener un registro específico por ID
    @GET("/api/v1/production-record/{recordId}")
    suspend fun getProductionRecord(@Path("recordId") recordId: UUID): Response<ProductionHistoryResponse>

    // Eliminar un registro
    @DELETE("/api/v1/production-record/{recordId}")
    suspend fun deleteProductionRecord(@Path("recordId") recordId: UUID): Response<Unit>

    // Obtener registros por ID de lote
    @GET("/api/v1/production-record/batch/{batchId}")
    suspend fun getProductionRecordsByBatch(@Path("batchId") batchId: UUID): Response<List<ProductionHistoryResponse>>

    // Obtener todos los registros
    @GET("/api/v1/production-record")
    suspend fun getAllProductionRecords(): Response<List<ProductionHistoryResponse>>

    // Crear un nuevo registro
    @POST("/api/v1/production-record")
    suspend fun createProductionRecord(@Body request: ProductionRecordRequest): Response<ProductionHistoryResponse>

    // Actualizar volumen de producción
    @PUT("/api/v1/production-record/{recordId}/volume")
    suspend fun updateProductionVolume(
        @Path("recordId") recordId: UUID,
        @Body request: UpdateVolumeRequest
    ): Response<ProductionHistoryResponse>
}