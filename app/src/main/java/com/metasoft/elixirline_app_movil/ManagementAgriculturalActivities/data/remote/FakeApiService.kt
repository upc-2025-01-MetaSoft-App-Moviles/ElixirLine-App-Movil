package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.EvidencePhotoDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskNotificationDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskExecutionReportDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.WeatherDto

class FakeApiService : ApiService {
    override suspend fun getTasks(): List<TaskDto> {
        return listOf(
            TaskDto(
                taskId = "uuid-task-1",
                title = "Riego en lote A",
                description = "Riego temprano",
                parcelId = "uuid-parcel-1",
                assignedTo = "uuid-user-1",
                scheduledDate = "2025-06-13T08:00:00Z",
                status = 0
            )
        )
    }

    override suspend fun getParcels(): List<ParcelDto> {
        return listOf(
            ParcelDto(
                parcelId = "uuid-parcel-1",
                name = "Lote A",
                area = 1.5,
                cropType = "Maíz",
                location = "Valle Central"
            )
        )
    }

    override suspend fun getWeather(): WeatherDto {
        return WeatherDto(
            temperatura = "28°C",
            descripcion = "Soleado",
            humedad = "60%",
            viento = "10 km/h"
        )
    }

    override suspend fun getReports(): List<TaskExecutionReportDto> {
        return listOf(
            TaskExecutionReportDto(
                reportId = "1",
                taskId = "1",
                executorId = "user-123",
                executionDate = "2025-06-13T08:00:00Z",
                observations = "Riego completado sin inconvenientes",
                evidencePhotos = listOf(
                    EvidencePhotoDto(
                        evidencePhotoId = "photo-1",
                        reportId = "1",
                        photoUrl = "https://example.com/photo1.jpg"
                    )
                )
            )
        )
    }

    override suspend fun getNotifications(): List<TaskNotificationDto> {
        return listOf(
            TaskNotificationDto(
                notificationId = "notif-1",
                recipientId = "user-123",
                taskId = "1",
                message = "Actividad de riego programada",
                sentDate = "2025-06-12T10:00:00Z",
                readStatus = false
            )
        )
    }
}


