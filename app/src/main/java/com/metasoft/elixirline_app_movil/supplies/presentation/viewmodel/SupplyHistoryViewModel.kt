package com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.domain.model.SupplyUsage
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetAllSupplyUsagesUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetSupplyByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SupplyHistoryViewModel(
    private val getAllSupplyUsagesUseCase: GetAllSupplyUsagesUseCase,
    private val getSupplyByIdUseCase: GetSupplyByIdUseCase
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val usages: List<SupplyUsageWithDetails>) : UiState()
        data class Error(val message: String) : UiState()
    }

    data class SupplyUsageWithDetails(
        val usage: SupplyUsage,
        val supplyName: String,
        val supplyCategory: String,
        val supplyUnit: String
    )

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredUsages = MutableStateFlow<List<SupplyUsageWithDetails>>(emptyList())
    val filteredUsages: StateFlow<List<SupplyUsageWithDetails>> = _filteredUsages

    private var allUsages: List<SupplyUsageWithDetails> = emptyList()

    init {
        loadSupplyUsages()
    }

    fun loadSupplyUsages() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val usages = getAllSupplyUsagesUseCase()
                val usagesWithDetails = mutableListOf<SupplyUsageWithDetails>()

                for (usage in usages) {
                    val supply = getSupplyByIdUseCase(usage.supplyId)
                    if (supply != null) {
                        usagesWithDetails.add(
                            SupplyUsageWithDetails(
                                usage = usage,
                                supplyName = supply.name,
                                supplyCategory = supply.category,
                                supplyUnit = supply.unit
                            )
                        )
                    }
                }

                allUsages = usagesWithDetails
                _filteredUsages.value = usagesWithDetails
                _uiState.value = UiState.Success(usagesWithDetails)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterUsages()
    }

    private fun filterUsages() {
        val query = searchQuery.value.lowercase()
        if (query.isEmpty()) {
            _filteredUsages.value = allUsages
        } else {
            _filteredUsages.value = allUsages.filter {
                it.supplyName.lowercase().contains(query) ||
                        it.supplyCategory.lowercase().contains(query) ||
                        it.usage.activity.lowercase().contains(query) ||
                        it.usage.date.lowercase().contains(query)
            }
        }
    }
}
