package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.model.ParcelDto
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Weather
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetParcelsUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetWeatherUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getParcelsUseCase: GetParcelsUseCase,
    private val parcelRepository: ParcelRepository
) : ViewModel() {

    private val _weatherInfo = MutableStateFlow<Weather?>(null)
    val weatherInfo: StateFlow<Weather?> = _weatherInfo.asStateFlow()

    private val _Tasks = MutableStateFlow<List<Task>>(emptyList())
    val Tasks: StateFlow<List<Task>> = _Tasks.asStateFlow()

    private val _parcels = MutableStateFlow<List<Parcel>>(emptyList())
    val parcels: StateFlow<List<Parcel>> = _parcels.asStateFlow()

    init {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            _weatherInfo.value = getWeatherUseCase()
            _Tasks.value = getTasksUseCase()
            _parcels.value = getParcelsUseCase()
        }
    }

    fun loadParcels() {
        viewModelScope.launch {
            val newParcels = getParcelsUseCase()
            println("loadParcels() llamado. Nuevos lotes: $newParcels")
            _parcels.value = newParcels
        }
    }


    fun addParcel(parcel: Parcel, onComplete: () -> Unit) {
        viewModelScope.launch {
            println("[ViewModel] Llamando a addParcel con: $parcel")
            parcelRepository.addParcel(parcel)
            val updatedParcels = parcelRepository.getParcels()
            println("[ViewModel] Parcels actualizados desde repository: $updatedParcels")
            _parcels.value = updatedParcels
            onComplete()
        }
    }

}