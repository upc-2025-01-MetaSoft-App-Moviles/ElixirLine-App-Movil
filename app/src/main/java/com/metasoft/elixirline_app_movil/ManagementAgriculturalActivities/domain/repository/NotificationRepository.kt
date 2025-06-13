package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.TaskNotification

interface NotificationRepository {
    suspend fun getNotifications(taskId: String): List<TaskNotification>
}

