package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskRepository


class GetTasksUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(): List<Task> = repository.getTasks()
}