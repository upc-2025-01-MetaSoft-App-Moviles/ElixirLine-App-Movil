package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.TaskExecutionReport

interface TaskReportRepository {
    suspend fun getReports(taskId: String): List<TaskExecutionReport>
}
