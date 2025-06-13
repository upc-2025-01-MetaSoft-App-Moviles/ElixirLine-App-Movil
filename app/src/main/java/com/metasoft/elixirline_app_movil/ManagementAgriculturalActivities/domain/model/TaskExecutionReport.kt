package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model

data class TaskExecutionReport(
    val reportId: String,
    val taskId: String,
    val executorId: String,
    val executionDate: String,
    val observations: String?,
    val evidencePhotos: List<EvidencePhoto>
)