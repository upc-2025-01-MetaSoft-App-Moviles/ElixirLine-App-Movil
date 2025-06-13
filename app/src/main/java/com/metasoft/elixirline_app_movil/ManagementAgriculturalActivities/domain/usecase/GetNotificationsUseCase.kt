package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.TaskNotification
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.NotificationRepository

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(taskId: String): List<TaskNotification> {
        return repository.getNotifications(taskId)
    }
}

