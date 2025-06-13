package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.TaskNotification
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.NotificationRepository

class NotificationRepositoryImpl(private val api: ApiService) : NotificationRepository {
    override suspend fun getNotifications(taskId: String): List<TaskNotification> {
        return api.getNotifications(taskId).map {
            TaskNotification(
                notificationId = it.notificationId,
                recipientId = it.recipientId,
                taskId = it.taskId,
                message = it.message,
                sentDate = it.sentDate,
                readStatus = it.readStatus
            )
        }
    }
}
