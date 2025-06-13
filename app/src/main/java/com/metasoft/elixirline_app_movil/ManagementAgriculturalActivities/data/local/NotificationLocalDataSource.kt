package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.TaskNotification
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.NotificationRepository

class NotificationLocalDataSource : NotificationRepository {
    override suspend fun getNotifications(): List<TaskNotification> {
        return listOf(
            TaskNotification("n1", "u1", "t1", "Tienes una nueva tarea", "2025-06-13T08:00:00", false)
        )
    }
}
