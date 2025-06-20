package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.ParcelRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.di.AppModule

class NuevoLoteViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NuevoLoteViewModel::class.java)) {
            val api = AppModule.provideApiService()
            val repo = ParcelRepositoryImpl(api)

            return NuevoLoteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}