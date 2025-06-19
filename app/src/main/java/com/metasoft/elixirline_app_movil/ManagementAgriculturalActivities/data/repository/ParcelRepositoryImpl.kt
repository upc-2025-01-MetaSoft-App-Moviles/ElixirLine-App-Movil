package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository


class ParcelRepositoryImpl(
    private val apiService: ApiService
) : ParcelRepository {

    override suspend fun getParcels(): List<Parcel> {
        return apiService.getParcels().map { dto ->
            Parcel(
                id = dto.parcelId,
                name = dto.name ?: "Nombre desconocido",
                cropType = dto.cropType ?: "Sin tipo de cultivo",
                growthStage = "Desconocido",
                lastTask = "Sin tareas",
                yieldEstimate = "Sin estimar"
            )
        }
    }

    override suspend fun addParcel(parcel: Parcel) {
        apiService.addParcel(
            ParcelDto(
                parcelId = parcel.id,
                name = parcel.name,
                area = 1.5,
                cropType = parcel.cropType,
                location = "Ubicación desconocida"
            )
        )
    }
}