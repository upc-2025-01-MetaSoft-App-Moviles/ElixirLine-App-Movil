package com.metasoft.elixirline_app_movil.presentation.di

import com.metasoft.elixirline_app_movil.data.di.DataModule
import com.metasoft.elixirline_app_movil.presentation.viewmodel.SeeAllProductionHistoryViewModel

object PresentationModule {
    fun getProductionHistoryViewModel(): SeeAllProductionHistoryViewModel {
        return SeeAllProductionHistoryViewModel(DataModule.getProductionHistoryRepository())
    }
}