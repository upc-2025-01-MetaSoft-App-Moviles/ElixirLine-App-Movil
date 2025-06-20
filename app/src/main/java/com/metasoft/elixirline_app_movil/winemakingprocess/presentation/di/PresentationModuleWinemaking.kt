package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.di

import com.metasoft.elixirline_app_movil.winemakingprocess.data.di.DataModuleWinemaking
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel.StagesByWineBatchViewModel
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel.WineBatchesListViewModel

object PresentationModuleWinemaking {

    // Provides a ViewModel for the Wine Batches List
    fun getWineBatchesListViewModel(): WineBatchesListViewModel {
        return WineBatchesListViewModel(DataModuleWinemaking.wineBatchRepository)
    }

    // Provides a ViewModel for the Stages by Wine Batch
    fun getStagesByWineBatchViewModel(): StagesByWineBatchViewModel {
        return StagesByWineBatchViewModel(DataModuleWinemaking.wineBatchRepository)
    }

}