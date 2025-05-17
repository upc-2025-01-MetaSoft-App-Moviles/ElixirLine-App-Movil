package com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.CreateSupplyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SupplyRegistrationViewModel(private val createSupplyUseCase: CreateSupplyUseCase) : ViewModel() {

    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState

    // Form fields
    val name = MutableStateFlow("")
    val category = MutableStateFlow("")
    val quantity = MutableStateFlow("")
    val unit = MutableStateFlow("")
    val location = MutableStateFlow("")
    val expirationDate = MutableStateFlow("")

    fun validateForm(): Boolean {
        return name.value.isNotBlank() &&
                category.value.isNotBlank() &&
                quantity.value.isNotBlank() &&
                unit.value.isNotBlank()
    }

    fun registerSupply() {
        if (!validateForm()) {
            _uiState.value = UiState.Error("Todos los campos marcados son obligatorios")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val quantityValue = quantity.value.toFloatOrNull() ?: 0f

                val supply = Supply(
                    id = UUID.randomUUID(),
                    name = name.value,
                    category = category.value,
                    quantity = quantityValue,
                    unit = unit.value,
                    location = location.value,
                    expirationDate = expirationDate.value,
                    status = "Disponible"
                )

                val result = createSupplyUseCase(supply)

                if (result) {
                    _uiState.value = UiState.Success
                    clearForm()
                } else {
                    _uiState.value = UiState.Error("No se pudo registrar el insumo")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    private fun clearForm() {
        name.value = ""
        category.value = ""
        quantity.value = ""
        unit.value = ""
        location.value = ""
        expirationDate.value = ""
    }
}
