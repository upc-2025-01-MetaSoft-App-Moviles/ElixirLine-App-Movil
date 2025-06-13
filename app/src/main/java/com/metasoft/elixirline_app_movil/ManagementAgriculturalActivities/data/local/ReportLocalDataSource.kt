package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.*
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskReportRepository

class ReportLocalDataSource : TaskReportRepository {
    override suspend fun getReports(): List<TaskExecutionReport> {
        return listOf(
            TaskExecutionReport(
                "r1", "t1", "e1", "2025-06-13T08:00:00", "Todo bien",
                listOf(
                    EvidencePhoto("p1", "r1", "https://foto1.com")
                )
            )
        )
    }
}
