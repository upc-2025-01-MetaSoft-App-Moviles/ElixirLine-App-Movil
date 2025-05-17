
package com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetSupplyByIdUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.UpdateSupplyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SupplyDetailViewModel(
    private val getSupplyByIdUseCase: GetSupplyByIdUseCase,
    private val updateSupplyUseCase: UpdateSupplyUseCase
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val supply: Supply) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    // Form fields
    val name = MutableStateFlow("")
    val category = MutableStateFlow("")
    val quantity = MutableStateFlow("")
    val unit = MutableStateFlow("")
    val location = MutableStateFlow("")
    val expirationDate = MutableStateFlow("")

    private var currentSupplyId: UUID? = null
    private var currentStatus: String = "Disponible"

    fun loadSupply(id: UUID) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val supply = getSupplyByIdUseCase(id)

                if (supply != null) {
                    currentSupplyId = supply.id
                    currentStatus = supply.status

                    // Update form fields
                    name.value = supply.name
                    category.value = supply.category
                    quantity.value = supply.quantity.toString()
                    unit.value = supply.unit
                    location.value = supply.location
                    expirationDate.value = supply.expirationDate

                    _uiState.value = UiState.Success(supply)
                } else {
                    _uiState.value = UiState.Error("Insumo no encontrado")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun validateForm(): Boolean {
        return name.value.isNotBlank() &&
                category.value.isNotBlank() &&
                quantity.value.isNotBlank() &&
                unit.value.isNotBlank()
    }

    fun updateSupply() {
        if (!validateForm() || currentSupplyId == null) {
            _uiState.value = UiState.Error("Todos los campos marcados son obligatorios")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val quantityValue = quantity.value.toFloatOrNull() ?: 0f

                val supply = Supply(
                    id = currentSupplyId!!,
                    name = name.value,
                    category = category.value,
                    quantity = quantityValue,
                    unit = unit.value,
                    location = location.value,
                    expirationDate = expirationDate.value,
                    status = currentStatus
                )

                val result = updateSupplyUseCase(supply)

                if (result) {
                    _uiState.value = UiState.Success(supply)
                } else {
                    _uiState.value = UiState.Error("No se pudo actualizar el insumo")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
