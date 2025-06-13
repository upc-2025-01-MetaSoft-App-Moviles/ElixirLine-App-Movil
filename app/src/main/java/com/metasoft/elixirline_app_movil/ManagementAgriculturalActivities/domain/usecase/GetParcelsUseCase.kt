package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository

class GetParcelsUseCase(private val repository: ParcelRepository) {
    suspend operator fun invoke(): List<Parcel> = repository.getParcels()
}
