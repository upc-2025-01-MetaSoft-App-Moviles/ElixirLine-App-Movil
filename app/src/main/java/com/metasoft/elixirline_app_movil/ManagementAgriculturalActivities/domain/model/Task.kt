package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val scheduledDate: String,
    val parcelId: String,
    val status: Int,
    val responsible: String
)