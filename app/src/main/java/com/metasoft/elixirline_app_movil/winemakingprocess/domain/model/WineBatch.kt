package com.metasoft.elixirline_app_movil.winemakingprocess.domain.model

import java.util.UUID

data class WineBatch(
    val id: UUID,
    val internalCode: String,
    val receptionDate: String,
    val harvestCampaign: String,
    val vineyardOrigin: String,
    val grapeVariety: String,
    val initialGrapeQuantityKg: Double,
    val createdBy: String,
    val status: String,
    val currentStage: String,
    )
