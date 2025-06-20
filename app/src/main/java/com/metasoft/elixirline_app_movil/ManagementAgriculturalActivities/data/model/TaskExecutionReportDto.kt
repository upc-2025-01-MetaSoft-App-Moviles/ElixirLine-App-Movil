package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model

data class TaskExecutionReportDto(
    val reportId: String,
    val taskId: String,
    val executorId: String,
    val executionDate: String,
    val observations: String?,
    val evidencePhotos: List<EvidencePhotoDto>
)
