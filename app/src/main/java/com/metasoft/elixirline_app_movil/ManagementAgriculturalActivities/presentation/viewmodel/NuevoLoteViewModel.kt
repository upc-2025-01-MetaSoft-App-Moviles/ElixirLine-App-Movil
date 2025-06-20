package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository
import kotlinx.coroutines.launch

class NuevoLoteViewModel(
    private val parcelRepository: ParcelRepository
) : ViewModel() {

    fun saveParcel(parcel: Parcel, onSaved: () -> Unit) {
        viewModelScope.launch {
            parcelRepository.addParcel(parcel)
            onSaved()
        }
    }
}