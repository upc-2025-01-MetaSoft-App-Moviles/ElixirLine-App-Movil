package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Weather
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetWeatherUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetTaskesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getTaskesUseCase: GetTaskesUseCase
) : ViewModel() {

    private val _weatherInfo = MutableStateFlow<Weather?>(null)
    val weatherInfo: StateFlow<Weather?> = _weatherInfo.asStateFlow()

    private val _Taskes = MutableStateFlow<List<Task>>(emptyList())
    val Taskes: StateFlow<List<Task>> = _Taskes.asStateFlow()

    init {
        viewModelScope.launch {
            _weatherInfo.value = getWeatherUseCase()
            _Taskes.value = getTaskesUseCase()
        }
    }
}