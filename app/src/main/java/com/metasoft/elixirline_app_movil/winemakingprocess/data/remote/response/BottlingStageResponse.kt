package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


data class BottlingStageResponse(

    @SerializedName("stage")
    val stage: String?,

    @SerializedName("registeredBy")
    val registeredBy: String?,

    @SerializedName("startDate")
    val startDate: String?,

    @SerializedName("endDate")
    val endDate: String?,

    @SerializedName("bottlingLine")
    val bottlingLine: String?,

    @SerializedName("bottlesFilled")
    val bottlesFilled: Int?,

    @SerializedName("bottleVolumeMl")
    val bottleVolumeMl: Int?,

    @SerializedName("totalVolumeLiters")
    val totalVolumeLiters: Double?,

    @SerializedName("sealType")
    val sealType: String?,

    @SerializedName("code")
    val code: String?,

    @SerializedName("temperature")
    val temperature: Double?,

    @SerializedName("wasFiltered")
    val wasFiltered: Boolean?,

    @SerializedName("wereLabelsApplied")
    val wereLabelsApplied: Boolean?,

    @SerializedName("wereCapsulesApplied")
    val wereCapsulesApplied: Boolean?,

    @SerializedName("comments")
    val comments: String?,

    @SerializedName("isCompleted")
    val isCompleted: Boolean?
)



