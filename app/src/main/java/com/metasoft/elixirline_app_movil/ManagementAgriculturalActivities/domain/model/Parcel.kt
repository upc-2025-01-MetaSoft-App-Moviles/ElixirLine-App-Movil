package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model

data class Parcel(
    val id: String,
    val name: String,
    val cropType: String,
    val location: String,
    val growthStage: String,
    val lastTask: String,
    val yieldEstimate: String,
    val status: String
)

