package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase

import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository

class AddParcelUseCase(
    private val repository: ParcelRepository
) {
    suspend operator fun invoke(parcel: Parcel) = repository.addParcel(parcel)
}
