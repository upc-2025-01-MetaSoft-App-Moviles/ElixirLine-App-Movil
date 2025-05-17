package com.metasoft.elixirline_app_movil.supplies.data.di

import com.metasoft.elixirline_app_movil.supplies.data.remote.ApiConstants
import com.metasoft.elixirline_app_movil.supplies.data.remote.SupplyService
import com.metasoft.elixirline_app_movil.supplies.data.repository.SupplyRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataModule {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val supplyService: SupplyService by lazy {
        retrofit.create(SupplyService::class.java)
    }

    val supplyRepository: SupplyRepository by lazy {
        SupplyRepository(supplyService)
    }
}
