package com.metasoft.elixirline_app_movil.fieldlog.data.di

import com.metasoft.elixirline_app_movil.fieldlog.data.remote.ApiConstants
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.FieldLogService
import com.metasoft.elixirline_app_movil.fieldlog.data.repository.FieldLogRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

    fun getFieldLogService(): FieldLogService {
        return getRetrofit().create(FieldLogService::class.java)
    }

    fun getFieldLogRepository(): FieldLogRepository {
        return FieldLogRepository(getFieldLogService())
    }

}