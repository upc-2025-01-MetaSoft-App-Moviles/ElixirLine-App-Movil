package com.metasoft.elixirline_app_movil.data.di

import androidx.room.Room
import com.metasoft.elixirline_app_movil.data.remote.ApiConstants
import com.metasoft.elixirline_app_movil.data.remote.ProductionHistoryService
import com.metasoft.elixirline_app_movil.data.repository.ProductionRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

    fun getProductionHistoryService(): ProductionHistoryService {
        return getRetrofit().create(ProductionHistoryService::class.java)
    }

    fun getProductionHistoryRepository(): ProductionRepository {
        return ProductionRepository(getProductionHistoryService())

    }

}