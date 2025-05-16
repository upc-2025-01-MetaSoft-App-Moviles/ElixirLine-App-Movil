package com.metasoft.elixirline_app_movil.winemakingprocess.data.repository

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


}
