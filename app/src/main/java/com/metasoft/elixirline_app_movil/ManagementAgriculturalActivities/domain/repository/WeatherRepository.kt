package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(): Weather
}