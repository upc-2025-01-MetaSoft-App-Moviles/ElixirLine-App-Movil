package com.metasoft.elixirline_app_movil.winemakingprocess.data.repository

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
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.service.WineBatchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WineBatchRepository(val wineBatchService: WineBatchService) {

    suspend fun getWineBatches(): List<WineBatchResponse> = withContext(Dispatchers.IO) {
        val response = wineBatchService.getWineBatches()
        return@withContext if (response.isSuccessful) response.body().orEmpty() else emptyList()
    }

    suspend fun getAllStages(): List<StagesResponse> = withContext(Dispatchers.IO) {
        val response = wineBatchService.getAllStages()
        return@withContext if (response.isSuccessful) response.body().orEmpty() else emptyList()
    }

    suspend fun getWineBatchById(batchId: String): WineBatchResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getWineBatchById(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getStagesByBatchId(batchId: String): List<StagesResponse> = withContext(Dispatchers.IO) {
        val response = wineBatchService.getStagesByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body().orEmpty() else emptyList()
    }

    suspend fun getReceptionStageByBatchId(batchId: String): ReceptionStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getReceptionStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getCorrectionStageByBatchId(batchId: String): CorrectionStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getCorrectionStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getFermentationStageByBatchId(batchId: String): FermentationStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getFermentationStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getPressingStageByBatchId(batchId: String): PressingStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getPressingStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getClarificationStageByBatchId(batchId: String): ClarificationStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getClarificationStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getAgingStageByBatchId(batchId: String): AgingStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getAgingStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getFiltrationStageByBatchId(batchId: String): FiltrationStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getFiltrationStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

    suspend fun getBottlingStageByBatchId(batchId: String): BottlingStageResponse? = withContext(Dispatchers.IO) {
        val response = wineBatchService.getBottlingStageByBatchId(batchId)
        return@withContext if (response.isSuccessful) response.body() else null
    }

}
