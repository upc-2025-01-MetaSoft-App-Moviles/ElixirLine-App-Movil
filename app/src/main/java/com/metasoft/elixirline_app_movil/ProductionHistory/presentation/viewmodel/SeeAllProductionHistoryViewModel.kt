package com.metasoft.elixirline_app_movil.ProductionHistory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.ProductionHistory.data.repository.ProductionRepository
import com.metasoft.elixirline_app_movil.ProductionHistory.domain.model.ProductionHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SeeAllProductionHistoryViewModel(val productionRepository: ProductionRepository) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val data: List<ProductionHistory>) : UiState()
        data class SuccessSingle(val data: ProductionHistory) : UiState()
        object SuccessOperation : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    /*fun findAllProductionHistory(recordId: UUID) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.findAllProductionHistory(recordId)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }*/

    fun getAllProductionRecords() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.getAllProductionRecords()
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun getProductionRecord(recordId: UUID) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.getProductionRecord(recordId)
                if (result != null) {
                    _uiState.value = UiState.SuccessSingle(result)
                } else {
                    _uiState.value = UiState.Error("No se encontró el registro")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun getProductionRecordsByBatch(batchId: UUID) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.getProductionRecordsByBatch(batchId)
                if (result.isNotEmpty()) {
                    _uiState.value = UiState.Success(result)
                } else {
                    _uiState.value = UiState.Error("No se encontraron registros para este lote")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun createProductionRecord(
        batchId: UUID,
        startDate: String,
        endDate: String,
        volumeProduced: Float,
        brix: Float,
        ph: Float,
        temperature: Float
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.createProductionRecord(
                    batchId, startDate, endDate, volumeProduced, brix, ph, temperature
                )
                if (result != null) {
                    _uiState.value = UiState.SuccessSingle(result)
                } else {
                    _uiState.value = UiState.Error("Error al crear el registro")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun updateProductionVolume(recordId: UUID, volumeProduced: Float) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.updateProductionVolume(recordId, volumeProduced)
                if (result != null) {
                    _uiState.value = UiState.SuccessSingle(result)
                } else {
                    _uiState.value = UiState.Error("Error al actualizar el volumen")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun deleteProductionRecord(recordId: UUID) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.deleteProductionRecord(recordId)
                if (result) {
                    _uiState.value = UiState.SuccessOperation
                } else {
                    _uiState.value = UiState.Error("Error al eliminar el registro")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}