package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository

class ParcelRepositoryImpl(private val api: ApiService) : ParcelRepository {
    override suspend fun getParcels(): List<Parcel> {
        return api.getParcels().map {
            Parcel(it.parcelId, it.name, it.area, it.cropType, it.location)
        }
    }
}
