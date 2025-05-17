package com.metasoft.elixirline_app_movil.ProductionHistory.presentation.di

import com.metasoft.elixirline_app_movil.ProductionHistory.data.di.DataModule
import com.metasoft.elixirline_app_movil.ProductionHistory.presentation.viewmodel.SeeAllProductionHistoryViewModel

object PresentationModule {
    fun getProductionHistoryViewModel(): SeeAllProductionHistoryViewModel {
        return SeeAllProductionHistoryViewModel(DataModule.getProductionHistoryRepository())
    }
}