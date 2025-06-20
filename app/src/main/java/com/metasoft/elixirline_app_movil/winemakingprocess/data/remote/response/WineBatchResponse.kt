package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

data class WineBatchResponse(

    @SerializedName( "id")
    val id: String,
    @SerializedName("internal_code")
    val internalCode: String,
    @SerializedName("reception_date")
    val receptionDate: String,
    @SerializedName("harvest_campaign")
    val harvestCampaign: String,
    @SerializedName("vineyard_origin")
    val vineyardOrigin: String,
    @SerializedName("grape_variety")
    val grapeVariety: String,
    @SerializedName("initial_grape_quantity_kg")
    val initialGrapeQuantityKg: Double,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("current_stage")
    val currentStage: String,
    @SerializedName("url_image")
    val urlImage: String,
)