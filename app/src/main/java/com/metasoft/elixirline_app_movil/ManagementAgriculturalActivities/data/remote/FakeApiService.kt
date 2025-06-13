package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.EvidencePhotoDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskNotificationDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskExecutionReportDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.WeatherDto

class FakeApiService : ApiService {

    private val fakeParcels = mutableListOf(
        ParcelDto("uuid-parcel-1", "Parcela Norte", 1.5, "Cabernet", "Valle Norte"),
        ParcelDto("uuid-parcel-2", "Viña El Molino", 1.5, "Merlot", "Valle Central"),
        ParcelDto("uuid-parcel-3", "Parcela Sur", 1.5, "Cabernet", "Valle Sur")
    )

    override suspend fun getParcels(): List<ParcelDto> {
        return fakeParcels
    }

    override suspend fun addParcel(parcel: ParcelDto): ParcelDto {
        fakeParcels.add(parcel)
        return parcel
    }

    override suspend fun getParcelById(parcelId: String): ParcelDto {
        return getParcels().firstOrNull { it.parcelId == parcelId }
            ?: throw NoSuchElementException("Parcela con ID $parcelId no encontrada")
    }

    override suspend fun updateParcel(parcelId: String, parcel: ParcelDto): ParcelDto {
        val index = fakeParcels.indexOfFirst { it.parcelId == parcelId }
        if (index != -1) {
            fakeParcels[index] = parcel
            return parcel
        } else {
            throw NoSuchElementException("Parcela con ID $parcelId no encontrada para actualizar")
        }
    }

    override suspend fun deleteParcel(parcelId: String) {
        val removed = fakeParcels.removeIf { it.parcelId == parcelId }
        if (!removed) {
            throw NoSuchElementException("Parcela con ID $parcelId no encontrada para eliminar")
        }
    }

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

    override suspend fun getTaskById(taskId: String): TaskDto {
        return TaskDto(
            taskId = taskId,
            title = "Tarea de ejemplo",
            description = "Descripción de ejemplo para la tarea $taskId",
            parcelId = "uuid-parcel-1",
            assignedTo = "uuid-user-1",
            scheduledDate = "2025-06-13T08:00:00Z",
            status = 0
        )
    }

    override suspend fun createTask(task: TaskDto): TaskDto {
        return task.copy(
            taskId = task.taskId.ifEmpty { "uuid-task-${System.currentTimeMillis()}" }
        )
    }

    override suspend fun updateTask(taskId: String, task: TaskDto): TaskDto {
        return task.copy(taskId = taskId)
    }

    override suspend fun deleteTask(taskId: String) {
    }

    override suspend fun getWeather(): WeatherDto {
        return WeatherDto(
            temperatura = "28°C",
            descripcion = "Soleado",
            humedad = "60%",
            viento = "10 km/h"
        )
    }

    override suspend fun getReports(taskId: String): List<TaskExecutionReportDto> {
        return listOf(
            TaskExecutionReportDto(
                reportId = "1",
                taskId = taskId,
                executorId = "user-123",
                executionDate = "2025-06-13T08:00:00Z",
                observations = "Riego completado sin inconvenientes",
                evidencePhotos = listOf(
                    EvidencePhotoDto("photo-1", "1", "https://example.com/photo1.jpg")
                )
            )
        )
    }

    override suspend fun getNotifications(taskId: String): List<TaskNotificationDto> {
        return listOf(
            TaskNotificationDto(
                notificationId = "notif-1",
                recipientId = "user-123",
                taskId = taskId,
                message = "Actividad de riego programada",
                sentDate = "2025-06-12T10:00:00Z",
                readStatus = false
            )
        )
    }
}
