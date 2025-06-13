package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local.TaskesLocalDataSource
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.local.WeatherLocalDataSource
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.WeatherRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetTaskesUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetWeatherUseCase

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val fakeApi = FakeApiService()

            val climaRepo = WeatherRepositoryImpl(fakeApi)
            val TaskesRepo = TaskRepositoryImpl(fakeApi)

            val climaUseCase = GetWeatherUseCase(climaRepo)
            val TaskesUseCase = GetTaskesUseCase(TaskesRepo)

            return MainViewModel(climaUseCase, TaskesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
