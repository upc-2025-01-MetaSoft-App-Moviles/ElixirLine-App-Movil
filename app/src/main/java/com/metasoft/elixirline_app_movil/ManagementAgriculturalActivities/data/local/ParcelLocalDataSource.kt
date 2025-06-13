package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository

class ParcelLocalDataSource : ParcelRepository {
    override suspend fun getParcels(): List<Parcel> {
        return listOf(
            Parcel("1", "Lote A", 10.5, "Cabernet", "Sector norte"),
            Parcel("2", "Lote B", 8.3, "Merlot", "Sector sur")
        )
    }
}
