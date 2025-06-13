package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.ParcelRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.WeatherRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.TaskRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetParcelsUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetTasksUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetWeatherUseCase

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val fakeApi = FakeApiService()

            val weatherRepo = WeatherRepositoryImpl(fakeApi)
            val taskRepo = TaskRepositoryImpl(fakeApi)
            val parcelRepo = ParcelRepositoryImpl(fakeApi)

            val weatherUseCase = GetWeatherUseCase(weatherRepo)
            val taskUseCase = GetTasksUseCase(taskRepo)
            val parcelUseCase = GetParcelsUseCase(parcelRepo)

            return MainViewModel(weatherUseCase, taskUseCase, parcelUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}