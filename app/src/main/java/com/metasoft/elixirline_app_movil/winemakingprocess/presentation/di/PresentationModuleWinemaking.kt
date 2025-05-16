package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.di

import com.metasoft.elixirline_app_movil.winemakingprocess.data.di.DataModuleWinemaking
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel.WineBatchesListViewModel

object PresentationModuleWinemaking {

    fun getWineBatchesListViewModel(): WineBatchesListViewModel {
        return WineBatchesListViewModel(DataModuleWinemaking.wineBatchRepository)
    }

}