package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model

data class Parcel(
    val parcelId: String,
    val name: String?,
    val area: Double,
    val cropType: String?,
    val location: String?
)
