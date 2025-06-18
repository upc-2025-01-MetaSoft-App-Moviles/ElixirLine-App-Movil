package com.metasoft.elixirline_app_movil.winemakingprocess.data.repository

import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.AgingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.BottlingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ClarificationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.CorrectionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FermentationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FiltrationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.PressingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ReceptionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.service.WineBatchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WineBatchRepository(val wineBatchService: WineBatchService) {

    suspend fun getWineBatches(): List<WineBatchResponse> = withContext(Dispatchers.IO) {

        // Call the API to get the WineBatches
        val response = wineBatchService.getWineBatches()

       if (response.isSuccessful){
            // If successful, return the list of WineBatches
            response.body()?.let {
                // If the body is not null, return the WineBatches
                return@withContext it
            }
       }

        // If the response is not successful, return an empty list
        return@withContext emptyList()
    }

    suspend fun getWineBatchById(batchId: String): WineBatchResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the WineBatch by batchId
        val response = wineBatchService.getWineBatchById(batchId)

        // If the response is successful, return the WineBatch
        if (response.isSuccessful) {

            // If the body is not null, return the WineBatch
            response.body()?.let {
                // If the body is not null, return the WineBatch
                return@withContext it
            }
        }

        // If the response is not successful, return null
        return@withContext null
    }









    suspend fun getReceptionStageByBatchId(batchId: String): ReceptionStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the ReceptionStage by batchId
        val response = wineBatchService.getReceptionStageByBatchId(batchId)

        if (response.isSuccessful) {

            // If successful, return the ReceptionStage
            response.body()?.let {
                // If the body is not null, return the ReceptionStage
                return@withContext it
            }
        }

        return@withContext null
    }

    suspend fun getCorrectionStageByBatchId(batchId: String): CorrectionStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the CorrectionStage by batchId
        val response = wineBatchService.getCorrectionStageByBatchId(batchId)

        // If the response is successful, return the CorrectionStage
        if (response.isSuccessful) {

            // If the body is not null, return the CorrectionStage
            response.body()?.let {

                // If the body is not null, return the CorrectionStage
                return@withContext it
            }
        }

        return@withContext null
    }

    suspend fun getFermentationStageByBatchId(batchId: String): FermentationStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the FermentationStage by batchId
        val response = wineBatchService.getFermentationStageByBatchId(batchId)

        // If the response is successful, return the FermentationStage
        if (response.isSuccessful) {
            // If the body is not null, return the FermentationStage
            response.body()?.let {
                // If the body is not null, return the FermentationStage
                return@withContext it
            }
        }

        return@withContext null
    }

    suspend fun getPressingStageByBatchId(batchId: String): PressingStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the PressingStage by batchId
        val response = wineBatchService.getPressingStageByBatchId(batchId)

        if (response.isSuccessful) {

            // If the response is successful, return the PressingStage
            response.body()?.let {
                // If the body is not null, return the PressingStage
                return@withContext it
            }
        }

        return@withContext null
    }

    suspend fun getClarificationStageByBatchId(batchId: String): ClarificationStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the ClarificationStage by batchId
        val response = wineBatchService.getClarificationStageByBatchId(batchId)

        if (response.isSuccessful) {

            // If the response is successful, return the ClarificationStage
            response.body()?.let {
                // If the body is not null, return the ClarificationStage
                return@withContext it
            }
        }

        return@withContext null
    }

    suspend fun getAgingStageByBatchId(batchId: String): AgingStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the AgingStage by batchId
        val response = wineBatchService.getAgingStageByBatchId(batchId)

        if (response.isSuccessful) {

            // If the response is successful, return the AgingStage
            response.body()?.let {
                // If the body is not null, return the AgingStage
                return@withContext it
            }
        }

        return@withContext null
    }

    suspend fun getFiltrationStageByBatchId(batchId: String): FiltrationStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the FiltrationStage by batchId
        val response = wineBatchService.getFiltrationStageByBatchId(batchId)

        // If the response is successful, return the FiltrationStage
        if (response.isSuccessful) {

            // If the body is not null, return the FiltrationStage
            response.body()?.let {
                // If the body is not null, return the FiltrationStage
                return@withContext it
            }
        }

        return@withContext null
    }

    suspend fun getBottlingStageByBatchId(batchId: String): BottlingStageResponse? = withContext(Dispatchers.IO) {

        // Call the API to get the BottlingStage by batchId
        val response = wineBatchService.getBottlingStageByBatchId(batchId)

        if (response.isSuccessful) {
            // If the response is successful, return the BottlingStage
            response.body()?.let {
                // If the body is not null, return the BottlingStage
                return@withContext it
            }
        }

        return@withContext null
    }



}
