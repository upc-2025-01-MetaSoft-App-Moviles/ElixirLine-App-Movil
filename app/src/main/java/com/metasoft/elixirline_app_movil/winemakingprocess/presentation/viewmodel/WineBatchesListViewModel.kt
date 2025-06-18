package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.repository.WineBatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WineBatchesListViewModel(val wineBatchRepository: WineBatchRepository): ViewModel() {

    // StateFlow to hold the list of WineBatchResponse
    private val _wineBatches = MutableStateFlow<List<WineBatchResponse>>(emptyList())
    val wineBatches: StateFlow<List<WineBatchResponse>> = _wineBatches

    // StateFlow to hold the selected WineBatchResponse
    private val _selectedBatch = MutableStateFlow<WineBatchResponse?>(null)
    val selectedBatch: StateFlow<WineBatchResponse?> = _selectedBatch



    // Function to fetch the list of wine batches
    fun getWineBatches() {
        viewModelScope.launch {
            _wineBatches.value = wineBatchRepository.getWineBatches()
        }
    }

    // Function to fetch a specific wine batch by its ID
    fun getWineBatchById(batchId: String) {
        viewModelScope.launch {
            _selectedBatch.value = wineBatchRepository.getWineBatchById(batchId)
        }
    }

}