
package com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetAllSuppliesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SupplyListViewModel(private val getAllSuppliesUseCase: GetAllSuppliesUseCase) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val supplies: List<Supply>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredSupplies = MutableStateFlow<List<Supply>>(emptyList())
    val filteredSupplies: StateFlow<List<Supply>> = _filteredSupplies

    private var allSupplies: List<Supply> = emptyList()

    init {
        loadSupplies()
    }

    fun loadSupplies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                allSupplies = getAllSuppliesUseCase()
                _filteredSupplies.value = allSupplies
                _uiState.value = UiState.Success(allSupplies)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterSupplies()
    }

    private fun filterSupplies() {
        val query = searchQuery.value.lowercase()
        if (query.isEmpty()) {
            _filteredSupplies.value = allSupplies
        } else {
            _filteredSupplies.value = allSupplies.filter {
                it.name.lowercase().contains(query) ||
                        it.category.lowercase().contains(query)
            }
        }
    }
}
