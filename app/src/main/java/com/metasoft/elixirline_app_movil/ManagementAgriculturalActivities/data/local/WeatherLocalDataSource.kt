package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Weather
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.WeatherRepository

class WeatherLocalDataSource : WeatherRepository {
    override suspend fun getWeather(): Weather {
        return Weather(
            temperatura = "26°C",
            descripcion = "Parcialmente nublado",
            humedad = "60%",
            viento = "12 km/h NE"
        )
    }
}
