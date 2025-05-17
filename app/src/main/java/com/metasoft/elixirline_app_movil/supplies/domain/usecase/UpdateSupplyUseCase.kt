package com.metasoft.elixirline_app_movil.supplies.domain.usecase

import com.metasoft.elixirline_app_movil.supplies.data.repository.SupplyRepository
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply

class UpdateSupplyUseCase(private val supplyRepository: SupplyRepository) {
    suspend operator fun invoke(supply: Supply): Boolean {
        return supplyRepository.updateSupply(supply)
    }
}
