package com.metasoft.elixirline_app_movil.ProductionHistory.data.remote

import com.metasoft.elixirline_app_movil.ProductionHistory.data.model.ProductionHistoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductionHistoryService {
    @GET("id")
    suspend fun findAllProductionHistory(): Response<List<ProductionHistoryResponse>>
}