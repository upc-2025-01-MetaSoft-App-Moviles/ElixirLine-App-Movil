package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.mapper

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel

fun ParcelDto.toDomain(): Parcel {
    return Parcel(
        id = parcelId,
        name = name,
        cropType = cropType,
        growthStage = growthStage,
        lastTask = lastTask,
        yieldEstimate = yieldEstimate,
        location = location,
        status = status
    )
}

fun Parcel.toDto(): ParcelDto {
    return ParcelDto(
        parcelId = id,
        name = name,
        area = 1.5,
        cropType = cropType,
        location = location,
        growthStage = growthStage,
        lastTask = lastTask,
        yieldEstimate = yieldEstimate,
        status = status
    )
}

