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
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun findAllProductionHistory(recordId: UUID) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = productionRepository.findAllProductionHistory(recordId)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}