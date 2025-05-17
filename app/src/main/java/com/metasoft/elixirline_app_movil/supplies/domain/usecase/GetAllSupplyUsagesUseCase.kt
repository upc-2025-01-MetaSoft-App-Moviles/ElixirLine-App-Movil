package com.metasoft.elixirline_app_movil.supplies.domain.usecase

import com.metasoft.elixirline_app_movil.supplies.data.repository.SupplyRepository
import com.metasoft.elixirline_app_movil.supplies.domain.model.SupplyUsage

class GetAllSupplyUsagesUseCase(private val supplyRepository: SupplyRepository) {
    suspend operator fun invoke(): List<SupplyUsage> {
        return supplyRepository.getAllSupplyUsages()
    }
}
