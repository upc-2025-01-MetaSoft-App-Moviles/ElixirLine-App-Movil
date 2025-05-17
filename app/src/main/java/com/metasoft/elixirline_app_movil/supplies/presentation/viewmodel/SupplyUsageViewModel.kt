package com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.domain.model.SupplyUsage
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetAllSuppliesUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetSupplyByIdUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.RegisterSupplyUsageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class SupplyUsageViewModel(
    private val getSupplyByIdUseCase: GetSupplyByIdUseCase,
    private val registerSupplyUsageUseCase: RegisterSupplyUsageUseCase,
    private val getAllSuppliesUseCase: GetAllSuppliesUseCase
) : ViewModel() {

    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState

    private val _supplies = MutableStateFlow<List<Supply>>(emptyList())
    val supplies: StateFlow<List<Supply>> = _supplies

    private val _selectedSupply = MutableStateFlow<Supply?>(null)
    val selectedSupply: StateFlow<Supply?> = _selectedSupply

    // Form fields
    val supplyId = MutableStateFlow<UUID?>(null)
    val batchId = MutableStateFlow<String>("B2024-VINEYARD01")
    val quantity = MutableStateFlow("")
    val activity = MutableStateFlow("")
    val date = MutableStateFlow("")
    val operatorName = MutableStateFlow("")

    init {
        loadSupplies()
        // Set current date
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        date.value = dateFormat.format(Date())
    }

    private fun loadSupplies() {
        viewModelScope.launch {
            try {
                _supplies.value = getAllSuppliesUseCase()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error al cargar insumos")
            }
        }
    }

    fun selectSupply(id: UUID) {
        viewModelScope.launch {
            try {
                val supply = getSupplyByIdUseCase(id)
                if (supply != null) {
                    _selectedSupply.value = supply
                    supplyId.value = id
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error al seleccionar insumo")
            }
        }
    }

    fun validateForm(): Boolean {
        return supplyId.value != null &&
                quantity.value.isNotBlank() &&
                activity.value.isNotBlank() &&
                date.value.isNotBlank()
    }

    fun registerUsage() {
        if (!validateForm()) {
            _uiState.value = UiState.Error("Todos los campos marcados son obligatorios")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val quantityValue = quantity.value.toFloatOrNull() ?: 0f
                val supply = getSupplyByIdUseCase(supplyId.value!!)

                if (supply == null) {
                    _uiState.value = UiState.Error("Insumo no encontrado")
                    return@launch
                }

                if (quantityValue <= 0) {
                    _uiState.value = UiState.Error("La cantidad debe ser mayor a 0")
                    return@launch
                }

                if (quantityValue > supply.quantity) {
                    _uiState.value = UiState.Error("No hay suficiente cantidad disponible")
                    return@launch
                }

                val batchUUID = try {
                    UUID.fromString(batchId.value)
                } catch (e: Exception) {
                    UUID.randomUUID() // Usar un UUID aleatorio si no es válido
                }

                val supplyUsage = SupplyUsage(
                    id = UUID.randomUUID(),
                    supplyId = supplyId.value!!,
                    batchId = batchUUID,
                    quantity = quantityValue,
                    activity = activity.value,
                    date = date.value,
                    operatorName = operatorName.value
                )

                val result = registerSupplyUsageUseCase(supplyUsage)

                if (result) {
                    _uiState.value = UiState.Success
                    clearForm()
                    loadSupplies() // Recargar los insumos para actualizar cantidades
                } else {
                    _uiState.value = UiState.Error("No se pudo registrar el uso del insumo")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    private fun clearForm() {
        supplyId.value = null
        _selectedSupply.value = null
        quantity.value = ""
        activity.value = ""
        // Mantener la fecha actual
        operatorName.value = ""
    }
}
