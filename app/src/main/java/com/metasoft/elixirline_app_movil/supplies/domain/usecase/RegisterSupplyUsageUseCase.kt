package com.metasoft.elixirline_app_movil.supplies.domain.usecase

import com.metasoft.elixirline_app_movil.supplies.data.repository.SupplyRepository
import com.metasoft.elixirline_app_movil.supplies.domain.model.SupplyUsage

class RegisterSupplyUsageUseCase(private val supplyRepository: SupplyRepository) {
    suspend operator fun invoke(supplyUsage: SupplyUsage): Boolean {
        return supplyRepository.registerSupplyUsage(supplyUsage)
    }
}
