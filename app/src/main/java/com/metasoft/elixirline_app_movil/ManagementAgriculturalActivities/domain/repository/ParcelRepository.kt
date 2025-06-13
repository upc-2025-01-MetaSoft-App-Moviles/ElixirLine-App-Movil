package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel

interface ParcelRepository {
    suspend fun getParcels(): List<Parcel>
}
