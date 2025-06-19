package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Weather
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val api: ApiService) : WeatherRepository {
    override suspend fun getWeather(): Weather {
        val dto = api.getWeather()
        return Weather(dto.temperatura, dto.descripcion, dto.humedad, dto.viento)
    }
}