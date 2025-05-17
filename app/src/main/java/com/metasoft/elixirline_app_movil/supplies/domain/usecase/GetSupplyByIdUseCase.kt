package com.metasoft.elixirline_app_movil.supplies.domain.usecase

import com.metasoft.elixirline_app_movil.supplies.data.repository.SupplyRepository
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import java.util.UUID

class GetSupplyByIdUseCase(private val supplyRepository: SupplyRepository) {
    suspend operator fun invoke(id: UUID): Supply? {
        return supplyRepository.getSupplyById(id)
    }
}
