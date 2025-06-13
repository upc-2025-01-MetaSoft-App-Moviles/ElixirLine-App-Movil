package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://elixirlineplatformapi20250517011708-fkhrbzgaerf0gffg.canadacentral-01.azurewebsites.net/swagger"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}