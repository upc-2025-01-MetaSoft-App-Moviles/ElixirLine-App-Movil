package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskRepository

class TasksLocalDataSource : TaskRepository {
    override suspend fun getTasks(): List<Task> {
        return listOf(
            Task("Hoy", "3:00 p.m", "Riego en Lote 3"),
            Task("Mañana", "8:00 a.m", "Aplicación de fertilizante Lote 2"),
            Task("Miércoles", "6:30 a.m", "Siembra en Parcela Norte")
        )
    }
}