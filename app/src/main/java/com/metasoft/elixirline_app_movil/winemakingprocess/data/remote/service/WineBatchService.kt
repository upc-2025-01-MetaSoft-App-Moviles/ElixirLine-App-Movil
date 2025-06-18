package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.service

import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.AgingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.BottlingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ClarificationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.CorrectionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FermentationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FiltrationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.PressingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ReceptionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import retrofit2.Response
import retrofit2.http.GET

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

   @GET("stages/{batchId}/receptionStage")
    suspend fun getReceptionStageByBatchId(batchId: Int): Response<ReceptionStageResponse>

    @GET("stages/{batchId}/correctionStage")
    suspend fun getCorrectionStageByBatchId(batchId: Int): Response<CorrectionStageResponse>

    @GET("stages/{batchId}/fermentationStage")
    suspend fun getFermentationStageByBatchId(batchId: Int): Response<FermentationStageResponse>

    @GET("stages/{batchId}/pressingStage")
    suspend fun getPressingStageByBatchId(batchId: Int): Response<PressingStageResponse>

    @GET("stages/{batchId}/clarificationStage")
    suspend fun getClarificationStageByBatchId(batchId: Int): Response<ClarificationStageResponse>

    @GET("stages/{batchId}/agingStage")
    suspend fun getAgingStageByBatchId(batchId: Int): Response<AgingStageResponse>

    @GET("stages/{batchId}/filtrationStage")
    suspend fun getFiltrationStageByBatchId(batchId: Int): Response<FiltrationStageResponse>

    @GET("stages/{batchId}/bottlingStage")
    suspend fun getBottlingStageByBatchId(batchId: Int): Response<BottlingStageResponse>

}