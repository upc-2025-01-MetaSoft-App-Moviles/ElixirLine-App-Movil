package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskNotificationDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskExecutionReportDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("tasks")
    suspend fun getTasks(): List<TaskDto>

    @GET("parcels")
    suspend fun getParcels(): List<ParcelDto>

    @GET("weather")
    suspend fun getWeather(): WeatherDto

    @GET("tasks/{taskId}/reports")
    suspend fun getReports(): List<TaskExecutionReportDto>

    @GET("tasks/{taskId}/notifications")
    suspend fun getNotifications(): List<TaskNotificationDto>
}


