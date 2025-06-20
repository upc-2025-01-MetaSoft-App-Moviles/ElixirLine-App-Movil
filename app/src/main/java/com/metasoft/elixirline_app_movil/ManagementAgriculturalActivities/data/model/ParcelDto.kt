package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model

data class ParcelDto(
    val parcelId: String,
    val name: String,
    val area: Double,
    val cropType: String,
    val location: String,
    val growthStage: String,
    val lastTask: String
)
