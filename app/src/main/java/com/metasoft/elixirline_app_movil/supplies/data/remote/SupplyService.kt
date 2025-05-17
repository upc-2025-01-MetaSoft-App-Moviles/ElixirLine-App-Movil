package com.metasoft.elixirline_app_movil.supplies.data.remote

import com.metasoft.elixirline_app_movil.supplies.data.model.SupplyResponse
import com.metasoft.elixirline_app_movil.supplies.data.model.SupplyUsageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface SupplyService {
    @GET(ApiConstants.SUPPLIES_ENDPOINT)
    suspend fun getAllSupplies(): Response<List<SupplyResponse>>

    @GET("${ApiConstants.SUPPLIES_ENDPOINT}/{id}")
    suspend fun getSupplyById(@Path("id") id: UUID): Response<SupplyResponse>

    @POST(ApiConstants.SUPPLIES_ENDPOINT)
    suspend fun createSupply(@Body supply: SupplyResponse): Response<SupplyResponse>

    @PUT("${ApiConstants.SUPPLIES_ENDPOINT}/{id}")
    suspend fun updateSupply(@Path("id") id: UUID, @Body supply: SupplyResponse): Response<SupplyResponse>

    @DELETE("${ApiConstants.SUPPLIES_ENDPOINT}/{id}")
    suspend fun deleteSupply(@Path("id") id: UUID): Response<Unit>

    @GET(ApiConstants.SUPPLY_USAGE_ENDPOINT)
    suspend fun getAllSupplyUsages(): Response<List<SupplyUsageResponse>>

    @POST(ApiConstants.SUPPLY_USAGE_ENDPOINT)
    suspend fun registerSupplyUsage(@Body supplyUsage: SupplyUsageResponse): Response<SupplyUsageResponse>
}
