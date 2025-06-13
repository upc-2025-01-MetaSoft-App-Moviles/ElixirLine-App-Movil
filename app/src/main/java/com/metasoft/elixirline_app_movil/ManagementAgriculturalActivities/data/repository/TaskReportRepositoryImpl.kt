package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.EvidencePhoto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.TaskExecutionReport
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskReportRepository

class TaskReportRepositoryImpl(private val api: ApiService) : TaskReportRepository {
    override suspend fun getReports(): List<TaskExecutionReport> {
        return api.getReports().map { reportDto ->
            TaskExecutionReport(
                reportId = reportDto.reportId,
                taskId = reportDto.taskId,
                executorId = reportDto.executorId,
                executionDate = reportDto.executionDate,
                observations = reportDto.observations,
                evidencePhotos = reportDto.evidencePhotos.map { photo ->
                    EvidencePhoto(
                        evidencePhotoId = photo.evidencePhotoId,
                        reportId = photo.reportId,
                        photoUrl = photo.photoUrl
                    )
                }
            )
        }
    }
}