package com.metasoft.elixirline_app_movil.supplies.presentation.di

import com.metasoft.elixirline_app_movil.supplies.data.di.DataModule
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.CreateSupplyUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetAllSuppliesUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetAllSupplyUsagesUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.GetSupplyByIdUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.RegisterSupplyUsageUseCase
import com.metasoft.elixirline_app_movil.supplies.domain.usecase.UpdateSupplyUseCase
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyDetailViewModel
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyHistoryViewModel
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyListViewModel
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyRegistrationViewModel
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyUsageViewModel

object PresentationModule {

    // UseCases
    private val getAllSuppliesUseCase by lazy { GetAllSuppliesUseCase(DataModule.supplyRepository) }
    private val getSupplyByIdUseCase by lazy { GetSupplyByIdUseCase(DataModule.supplyRepository) }
    private val createSupplyUseCase by lazy { CreateSupplyUseCase(DataModule.supplyRepository) }
    private val updateSupplyUseCase by lazy { UpdateSupplyUseCase(DataModule.supplyRepository) }
    private val registerSupplyUsageUseCase by lazy { RegisterSupplyUsageUseCase(DataModule.supplyRepository) }
    private val getAllSupplyUsagesUseCase by lazy { GetAllSupplyUsagesUseCase(DataModule.supplyRepository) }

    // ViewModels
    fun getSupplyListViewModel(): SupplyListViewModel {
        return SupplyListViewModel(getAllSuppliesUseCase)
    }

    fun getSupplyRegistrationViewModel(): SupplyRegistrationViewModel {
        return SupplyRegistrationViewModel(createSupplyUseCase)
    }

    fun getSupplyDetailViewModel(): SupplyDetailViewModel {
        return SupplyDetailViewModel(getSupplyByIdUseCase, updateSupplyUseCase)
    }

    fun getSupplyUsageViewModel(): SupplyUsageViewModel {
        return SupplyUsageViewModel(getSupplyByIdUseCase, registerSupplyUsageUseCase, getAllSuppliesUseCase)
    }

    fun getSupplyHistoryViewModel(): SupplyHistoryViewModel {
        return SupplyHistoryViewModel(getAllSupplyUsagesUseCase, getSupplyByIdUseCase)
    }
}
