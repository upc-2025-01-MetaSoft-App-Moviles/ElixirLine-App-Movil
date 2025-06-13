package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskNotificationDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskExecutionReportDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.WeatherDto
import retrofit2.http.*

interface ApiService {

    // ---------- TAREAS ----------
    @GET("tasks")
    suspend fun getTasks(): List<TaskDto>

    @GET("tasks/{taskId}")
    suspend fun getTaskById(@Path("taskId") taskId: String): TaskDto

    @POST("tasks")
    suspend fun createTask(@Body task: TaskDto): TaskDto

    @PUT("tasks/{taskId}")
    suspend fun updateTask(@Path("taskId") taskId: String, @Body task: TaskDto): TaskDto

    @DELETE("tasks/{taskId}")
    suspend fun deleteTask(@Path("taskId") taskId: String)

    // ---------- LOTES (Parcels) ----------
    @GET("parcels")
    suspend fun getParcels(): List<ParcelDto>

    @GET("parcels/{parcelId}")
    suspend fun getParcelById(@Path("parcelId") parcelId: String): ParcelDto

    @POST("parcels")
    suspend fun addParcel(@Body parcel: ParcelDto): ParcelDto

    @PUT("parcels/{parcelId}")
    suspend fun updateParcel(@Path("parcelId") parcelId: String, @Body parcel: ParcelDto): ParcelDto

    @DELETE("parcels/{parcelId}")
    suspend fun deleteParcel(@Path("parcelId") parcelId: String)

    // ---------- CLIMA ----------
    @GET("weather")
    suspend fun getWeather(): WeatherDto

    // ---------- REPORTES ----------
    @GET("tasks/{taskId}/reports")
    suspend fun getReports(@Path("taskId") taskId: String): List<TaskExecutionReportDto>
/*
    @GET("reports/{reportId}")
    suspend fun getReportById(@Path("reportId") reportId: String): TaskExecutionReportDto

    @POST("tasks/{taskId}/reports")
    suspend fun createReport(@Path("taskId") taskId: String, @Body report: TaskExecutionReportDto): TaskExecutionReportDto

    @DELETE("reports/{reportId}")
    suspend fun deleteReport(@Path("reportId") reportId: String)
*/
    // ---------- NOTIFICACIONES ----------
    @GET("tasks/{taskId}/notifications")
    suspend fun getNotifications(@Path("taskId") taskId: String): List<TaskNotificationDto>
/*
    @GET("notifications/{notificationId}")
    suspend fun getNotificationById(@Path("notificationId") notificationId: String): TaskNotificationDto

    @POST("tasks/{taskId}/notifications")
    suspend fun createNotification(@Path("taskId") taskId: String, @Body notification: TaskNotificationDto): TaskNotificationDto

    @PUT("notifications/{notificationId}")
    suspend fun updateNotification(@Path("notificationId") notificationId: String, @Body notification: TaskNotificationDto): TaskNotificationDto

    @DELETE("notifications/{notificationId}")
    suspend fun deleteNotification(@Path("notificationId") notificationId: String)
*/
}


