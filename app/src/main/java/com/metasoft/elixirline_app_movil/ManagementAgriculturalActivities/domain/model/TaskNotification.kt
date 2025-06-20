package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model

data class TaskNotification(
    val notificationId: String,
    val recipientId: String,
    val taskId: String,
    val message: String?,
    val sentDate: String,
    val readStatus: Boolean
)