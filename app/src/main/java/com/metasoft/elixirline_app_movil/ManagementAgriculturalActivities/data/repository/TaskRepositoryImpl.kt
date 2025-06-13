package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val apiService: FakeApiService
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
}