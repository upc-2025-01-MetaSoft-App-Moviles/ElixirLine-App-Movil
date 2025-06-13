package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.mapper.toDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import kotlinx.coroutines.launch

class NuevoLoteViewModel(
    private val parcelRepository: FakeApiService
) : ViewModel() {

    fun saveParcel(parcel: Parcel, onSaved: () -> Unit) {
        viewModelScope.launch {
            parcelRepository.addParcel(parcel.toDto())
            onSaved()
        }
    }
}
