package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.repository.WineBatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WineBatchesListViewModel(val wineBatchRepository: WineBatchRepository): ViewModel() {

    private val _wineBatches = MutableStateFlow<List<WineBatchResponse>>(emptyList())
    val wineBatches: StateFlow<List<WineBatchResponse>> = _wineBatches

    fun getWineBatches() {
        viewModelScope.launch {
            _wineBatches.value = wineBatchRepository.getWineBatches()
        }
    }


}