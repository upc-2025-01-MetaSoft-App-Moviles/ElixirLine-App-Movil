package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


data class StagesResponse (

    @SerializedName("id")
    val id: String?,
    @SerializedName("batchId")
    val batchId: String?,
    @SerializedName("receptionStage")
    val receptionStage: ReceptionStageResponse?,
    @SerializedName("correctionStage")
    val correctionStage: CorrectionStageResponse?,
    @SerializedName("fermentationStage")
    val fermentationStage: FermentationStageResponse?,
    @SerializedName("pressingStage")
    val pressingStage: PressingStageResponse?,
    @SerializedName("clarificationStage")
    val clarificationStage: ClarificationStageResponse?,
    @SerializedName("agingStage")
    val agingStage: AgingStageResponse?,
    @SerializedName("filtrationStage")
    val filtrationStage: FiltrationStageResponse?,
    @SerializedName("bottlingStage")
    val bottlingStage: BottlingStageResponse?

)