package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.mapper

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel

fun ParcelDto.toDomain(): Parcel {
    return Parcel(
        id = parcelId ?: "",
        name = name ?: "",
        cropType = cropType ?: "",
        growthStage = "Etapa estimada",
        lastTask = "Sin actividad aún",
        yieldEstimate = "Sin estimar"
    )
}

fun Parcel.toDto(): ParcelDto {
    return ParcelDto(
        parcelId = this.id,
        name = this.name,
        area = 1.5,
        cropType = this.cropType,
        location = "Ubicación simulada"
    )
}
