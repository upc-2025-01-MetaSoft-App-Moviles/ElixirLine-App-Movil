package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.service

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


}