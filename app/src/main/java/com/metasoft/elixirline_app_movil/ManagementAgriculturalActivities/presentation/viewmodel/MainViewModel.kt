package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Weather
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetWeatherUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val _weatherInfo = MutableStateFlow<Weather?>(null)
    val weatherInfo: StateFlow<Weather?> = _weatherInfo.asStateFlow()

    private val _Tasks = MutableStateFlow<List<Task>>(emptyList())
    val Tasks: StateFlow<List<Task>> = _Tasks.asStateFlow()

    init {
        viewModelScope.launch {
            _weatherInfo.value = getWeatherUseCase()
            _Tasks.value = getTasksUseCase()
        }
    }
}