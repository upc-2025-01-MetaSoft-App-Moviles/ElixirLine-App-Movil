package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


data class CorrectionStageResponse(

    @SerializedName("stage")
    val stage: String?,

    @SerializedName("registeredBy")
    val registeredBy: String?,

    @SerializedName("startDate")
    val startDate: String?,

    @SerializedName("endDate")
    val endDate: String?,

    @SerializedName("initialBrix")
    val initialBrix: Double?,

    @SerializedName("finalBrix")
    val finalBrix: Double?,

    @SerializedName("addedSugarKg")
    val addedSugarKg: Double?,

    @SerializedName("initialPH")
    val initialPH: Double?,

    @SerializedName("finalPH")
    val finalPH: Double?,

    @SerializedName("acidType")
    val acidType: String?,

    @SerializedName("acidAddedGL")
    val acidAddedGL: Double?,

    @SerializedName("SO2AddedMgL")
    val SO2AddedMgL: Int?,

    @SerializedName("nutrientsAdded")
    val nutrientsAdded: List<NutrientEntity>?,

    @SerializedName("justification")
    val justification: String?,

    @SerializedName("comments")
    val comments: String?,

    @SerializedName("isCompleted")
    val isCompleted: Boolean?
)
