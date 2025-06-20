package com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.FieldLogService
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.model.FieldLogEntryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FieldLogHistoryViewModel(
    private val fieldLogService: FieldLogService
) : ViewModel() {

    private val _entries = MutableStateFlow<List<FieldLogEntryResponse>>(emptyList())
    val entries: StateFlow<List<FieldLogEntryResponse>> = _entries

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadEntries() {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            try {
                val response = fieldLogService.getFieldLogEntries()
                if (response.isSuccessful) {
                    _entries.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Excepción: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
