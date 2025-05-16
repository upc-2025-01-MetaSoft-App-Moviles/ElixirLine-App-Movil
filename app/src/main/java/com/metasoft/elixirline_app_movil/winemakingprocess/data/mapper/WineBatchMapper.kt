package com.metasoft.elixirline_app_movil.winemakingprocess.data.mapper

import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.domain.model.WineBatch
import java.util.UUID

object WineBatchMapper {

    fun toWineBatchEntity(wineBatchDto: WineBatchResponse): WineBatch {
        return WineBatch(
            id = UUID.fromString(wineBatchDto.id),
            internalCode = wineBatchDto.internalCode,
            receptionDate = wineBatchDto.receptionDate,
            harvestCampaign = wineBatchDto.harvestCampaign,
            vineyardOrigin = wineBatchDto.vineyardOrigin,
            grapeVariety = wineBatchDto.grapeVariety,
            initialGrapeQuantityKg = wineBatchDto.initialGrapeQuantityKg,
            createdBy = wineBatchDto.createdBy,
            status = wineBatchDto.status,
            currentStage = wineBatchDto.currentStage
        )
    }

}