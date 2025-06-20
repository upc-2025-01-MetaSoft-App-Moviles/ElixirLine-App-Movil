package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model

data class TaskNotificationDto(
    val notificationId: String,
    val recipientId: String,
    val taskId: String,
    val message: String?,
    val sentDate: String,
    val readStatus: Boolean
)
