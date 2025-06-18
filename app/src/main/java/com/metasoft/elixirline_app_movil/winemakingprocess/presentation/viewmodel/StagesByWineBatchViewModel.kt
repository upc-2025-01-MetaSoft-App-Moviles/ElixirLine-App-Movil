package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.AgingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.BottlingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ClarificationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.CorrectionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FermentationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FiltrationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.PressingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ReceptionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.repository.WineBatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StagesByWineBatchViewModel (val wineBatchRepository: WineBatchRepository): ViewModel() {


    // receptionStage =================================================================
    private val _receptionStage = MutableStateFlow<ReceptionStageResponse?>(null)
    val receptionStage: StateFlow<ReceptionStageResponse?> = _receptionStage

    fun getReceptionStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _receptionStage.value = wineBatchRepository.getReceptionStageByBatchId(batchId)
        }
    }

    // correctionStage ================================================================
    private val _correctionStage = MutableStateFlow<CorrectionStageResponse?>(null)
    val correctionStage: StateFlow<CorrectionStageResponse?> = _correctionStage

    fun getCorrectionStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _correctionStage.value = wineBatchRepository.getCorrectionStageByBatchId(batchId)
        }
    }

    // fermentationStage ==============================================================
    private val _fermentationStage = MutableStateFlow<FermentationStageResponse?>(null)
    val fermentationStage: StateFlow<FermentationStageResponse?> = _fermentationStage

    fun getFermentationStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _fermentationStage.value = wineBatchRepository.getFermentationStageByBatchId(batchId)
        }
    }

    // pressingStage ==================================================================
    private val _pressingStage = MutableStateFlow<PressingStageResponse?>(null)
    val pressingStage: StateFlow<PressingStageResponse?> = _pressingStage

    fun getPressingStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _pressingStage.value = wineBatchRepository.getPressingStageByBatchId(batchId)
        }
    }

    // clarificationStage ==============================================================
    private val _clarificationStage = MutableStateFlow<ClarificationStageResponse?>(null)
    val clarificationStage: StateFlow<ClarificationStageResponse?> = _clarificationStage

    fun getClarificationStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _clarificationStage.value = wineBatchRepository.getClarificationStageByBatchId(batchId)
        }
    }

    // agingStage ==================================================================
    private val _agingStage = MutableStateFlow<AgingStageResponse?>(null)
    val agingStage: StateFlow<AgingStageResponse?> = _agingStage

    fun getAgingStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _agingStage.value = wineBatchRepository.getAgingStageByBatchId(batchId)
        }
    }

    // FiltrationStage ==================================================================
    private val _filtrationStage = MutableStateFlow<FiltrationStageResponse?>(null)
    val filtrationStage: StateFlow<FiltrationStageResponse?> = _filtrationStage

    fun getFiltrationStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _filtrationStage.value = wineBatchRepository.getFiltrationStageByBatchId(batchId)
        }
    }

    // BottlingStage ==================================================================
    private val _bottlingStage = MutableStateFlow<BottlingStageResponse?>(null)
    val bottlingStage: StateFlow<BottlingStageResponse?> = _bottlingStage

    fun getBottlingStageByBatchId(batchId: String) {
        viewModelScope.launch {
            _bottlingStage.value = wineBatchRepository.getBottlingStageByBatchId(batchId)
        }
    }

}

