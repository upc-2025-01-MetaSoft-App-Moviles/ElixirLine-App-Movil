package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.ParcelRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.WeatherRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.TaskRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetParcelsUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetTasksUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetWeatherUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.di.AppModule

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val api = AppModule.provideApiService()

            val weatherRepo = WeatherRepositoryImpl(api)
            val taskRepo = TaskRepositoryImpl(api)
            val parcelRepo = ParcelRepositoryImpl(api)

            val weatherUseCase = GetWeatherUseCase(weatherRepo)
            val taskUseCase = GetTasksUseCase(taskRepo)
            val parcelUseCase = GetParcelsUseCase(parcelRepo)

            return MainViewModel(weatherUseCase, taskUseCase, parcelUseCase, parcelRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}