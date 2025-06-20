package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.service

import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.AgingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.BottlingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ClarificationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.CorrectionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FermentationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FiltrationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.PressingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ReceptionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.StagesResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WineBatchService {

    /**
     * Fetches a list of wine batches from the API.
     *
     * This function is a suspend function, meaning it can be called from a coroutine or another suspend function.
     *
     * @return A list of [WineBatchResponse] objects representing the wine batches.
        * @throws Exception if the network request fails or if the response cannot be parsed.
     */
    @GET("wine_batches")
    suspend fun getWineBatches(): Response<List<WineBatchResponse>>

    /**
     * Fetches a specific wine batch by its ID.
     *
     * @param batchId The ID of the wine batch to fetch.
     * @return A [WineBatchResponse] object representing the wine batch, or null if not found.
     */
    @GET("wine_batches/{batchId}")
    suspend fun getWineBatchById(@Path("batchId") batchId: String): Response<WineBatchResponse>


    @GET("stages")
    suspend fun getAllStages(): Response<List<StagesResponse>>

    @GET("stages")
    suspend fun getStagesByBatchId(@Query("batchId") batchId: String): Response<List<StagesResponse>>



    @GET("stages/{batchId}/receptionStage")
    suspend fun getReceptionStageByBatchId(@Path("batchId") batchId: String): Response<ReceptionStageResponse>

    @GET("batches/{batchId}/stages/correction")
    suspend fun getCorrectionStageByBatchId(@Path("batchId") batchId: String): Response<CorrectionStageResponse>

    @GET("stages/{batchId}/fermentationStage")
    suspend fun getFermentationStageByBatchId(@Path("batchId") batchId: String): Response<FermentationStageResponse>

    @GET("stages/{batchId}/pressingStage")
    suspend fun getPressingStageByBatchId(@Path("batchId") batchId: String): Response<PressingStageResponse>

    @GET("stages/{batchId}/clarificationStage")
    suspend fun getClarificationStageByBatchId(@Path("batchId") batchId: String): Response<ClarificationStageResponse>

    @GET("stages/{batchId}/agingStage")
    suspend fun getAgingStageByBatchId(@Path("batchId") batchId: String): Response<AgingStageResponse>

    @GET("stages/{batchId}/filtrationStage")
    suspend fun getFiltrationStageByBatchId(@Path("batchId") batchId: String): Response<FiltrationStageResponse>

    @GET("stages/{batchId}/bottlingStage")
    suspend fun getBottlingStageByBatchId(@Path("batchId") batchId: String): Response<BottlingStageResponse>

}