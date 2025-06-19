package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.TaskDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val apiService: ApiService
) : TaskRepository {

    override suspend fun getTasks(): List<Task> {
        return apiService.getTasks().map { dto ->
            Task(
                id = dto.taskId,
                title = dto.title,
                description = dto.description,
                scheduledDate = dto.scheduledDate
            )
        }
    }

    override suspend fun addTask(task: Task) {
        apiService.createTask(
            TaskDto(
                taskId = task.id,
                title = task.title,
                description = task.description,
                parcelId = "uuid-parcel-1",
                assignedTo = "uuid-user-1",
                scheduledDate = task.scheduledDate,
                status = 0
            )
        )
    }
}
