package com.metasoft.elixirline_app_movil.winemakingprocess.domain.usecase

import com.metasoft.elixirline_app_movil.winemakingprocess.data.repository.WineBatchRepository

class GetWineBatchesUseCase (private val repository: WineBatchRepository) {
    suspend operator fun invoke() = repository.getWineBatches()
}