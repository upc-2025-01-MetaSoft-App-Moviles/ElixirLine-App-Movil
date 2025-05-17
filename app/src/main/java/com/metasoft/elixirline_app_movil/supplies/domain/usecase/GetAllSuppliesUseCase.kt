package com.metasoft.elixirline_app_movil.supplies.domain.usecase

import com.metasoft.elixirline_app_movil.supplies.data.repository.SupplyRepository
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply

class GetAllSuppliesUseCase(private val supplyRepository: SupplyRepository) {
    suspend operator fun invoke(): List<Supply> {
        return supplyRepository.getAllSupplies()
    }
}
