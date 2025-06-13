package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService

class NuevoLoteViewModelFactory(
    private val parcelRepository: FakeApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NuevoLoteViewModel::class.java)) {
            return NuevoLoteViewModel(parcelRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}