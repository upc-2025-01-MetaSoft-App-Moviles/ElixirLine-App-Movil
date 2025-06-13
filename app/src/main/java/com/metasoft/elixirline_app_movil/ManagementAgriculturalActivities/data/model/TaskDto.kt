package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model

data class TaskDto(
    val taskId: String,
    val title: String,
    val description: String,
    val parcelId: String,
    val assignedTo: String,
    val scheduledDate: String,
    val status: Int
)
