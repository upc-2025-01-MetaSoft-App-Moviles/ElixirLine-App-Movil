package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.mapper.toDomain
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.mapper.toDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository

class ParcelRepositoryImpl(
    private val apiService: ApiService
) : ParcelRepository {
    override suspend fun getParcels(): List<Parcel> {
        return apiService.getParcels().map { it.toDomain() }
    }

    override suspend fun addParcel(parcel: Parcel) {
        apiService.addParcel(parcel.toDto())
    }
}

