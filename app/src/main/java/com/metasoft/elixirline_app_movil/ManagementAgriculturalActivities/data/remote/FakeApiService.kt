package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote

import com.google.android.datatransport.runtime.BuildConfig
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.EvidencePhotoDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskNotificationDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskExecutionReportDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.WeatherDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel

class FakeApiService : ApiService {

    private val fakeParcels = mutableListOf<ParcelDto>()

    override suspend fun getParcels(): List<ParcelDto> {
        return fakeParcels
    }

    override suspend fun addParcel(parcel: ParcelDto): ParcelDto {
        println("Se añadió un nuevo ParcelDto: $parcel")
        fakeParcels.add(parcel)
        println("Lista actual de fakeParcels: $fakeParcels")
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

    private val fakeTasks = mutableListOf<TaskDto>()

    override suspend fun getTasks(): List<TaskDto> {
        return fakeTasks
    }

    override suspend fun createTask(task: TaskDto): TaskDto {
        val newTask = task.copy(
            taskId = task.taskId.ifEmpty { "uuid-task-${System.currentTimeMillis()}" }
        )
        fakeTasks.add(newTask)

        val parcelIndex = fakeParcels.indexOfFirst { it.parcelId == newTask.parcelId }
        if (parcelIndex != -1) {
            val updatedParcel = fakeParcels[parcelIndex].copy(lastTask = newTask.title)
            fakeParcels[parcelIndex] = updatedParcel
            println("ParcelDto actualizado con nueva tarea: ${fakeParcels[parcelIndex]}")
        } else {
            println("No se encontró el ParcelDto para parcelId: ${newTask.parcelId}")
        }

        return newTask
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

    override suspend fun updateTask(taskId: String, task: TaskDto): TaskDto {
        return task.copy(taskId = taskId)
    }

    override suspend fun deleteTask(taskId: String) {
    }

    override suspend fun getWeather(): WeatherDto {
        val temperatura = (20..35).random().toString() + "°C"
        val descripciones = listOf("Soleado", "Nublado", "Lluvia", "Parcialmente nublado", "Tormenta")
        val descripcion = descripciones.random()
        val humedad = (40..80).random().toString() + "%"
        val viento = (5..20).random().toString() + " km/h"

        return WeatherDto(
            temperatura = temperatura,
            descripcion = descripcion,
            humedad = humedad,
            viento = viento
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
