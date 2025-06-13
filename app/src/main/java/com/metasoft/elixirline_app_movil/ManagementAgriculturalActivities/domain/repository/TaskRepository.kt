package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): List<Task>
}