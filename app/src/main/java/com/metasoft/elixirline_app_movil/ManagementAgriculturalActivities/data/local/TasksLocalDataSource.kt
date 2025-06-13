package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskRepository

class TasksLocalDataSource : TaskRepository {
    override suspend fun getTasks(): List<Task> {
        return listOf(
            Task(
                id = "1",
                title = "Hoy",
                description = "Riego en Lote 3",
                scheduledDate = "2025-06-12T15:00:00Z"
            ),
            Task(
                id = "2",
                title = "Mañana",
                description = "Aplicación de fertilizante Lote 2",
                scheduledDate = "2025-06-13T08:00:00Z"
            ),
            Task(
                id = "3",
                title = "Miércoles",
                description = "Siembra en Parcela Norte",
                scheduledDate = "2025-06-14T06:30:00Z"
            )
        )
    }
}