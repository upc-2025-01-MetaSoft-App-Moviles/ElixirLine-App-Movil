package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.TaskExecutionReport
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskReportRepository

class GetReportsUseCase(private val repository: TaskReportRepository) {
    suspend operator fun invoke(taskId: String): List<TaskExecutionReport> {
        return repository.getReports(taskId)
    }
}

