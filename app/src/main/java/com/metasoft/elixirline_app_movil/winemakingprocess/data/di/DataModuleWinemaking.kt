package com.metasoft.elixirline_app_movil.winemakingprocess.data.di

import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.ApiClientWinemaking.BASE_URL
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.service.WineBatchService
import com.metasoft.elixirline_app_movil.winemakingprocess.data.repository.WineBatchRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModuleWinemaking {

    // Retrofit singleton
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    /***
     * Services
     * - WineBatchService
     * - WineBatchRepository
     *
     * This module is responsible for providing the necessary dependencies for the data layer of the application.
     * It includes the Retrofit instance and the repositories that will be used to interact with the API.
     *
     */
    // ========= Services - Repositories =========
    val wineBatchService: WineBatchService by lazy {
        retrofit.create(WineBatchService::class.java)
    }

    val wineBatchRepository: WineBatchRepository by lazy {
        WineBatchRepository(wineBatchService)
    }







}