package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


data class ClarificationStageResponse(

    @SerializedName("stage")
    val stage: String?,

    @SerializedName("registeredBy")
    val registeredBy: String?,

    @SerializedName("startDate")
    val startDate: String?,

    @SerializedName("endDate")
    val endDate: String?,

    @SerializedName("method")
    val method: String?,

    @SerializedName("clarifyingAgents")
    val clarifyingAgents: List<ClarifyingAgentEntity>?,

    @SerializedName("turbidityBeforeNTU")
    val turbidityBeforeNTU: Double?,

    @SerializedName("turbidityAfterNTU")
    val turbidityAfterNTU: Double?,

    @SerializedName("volumeLiters")
    val volumeLiters: Double?,

    @SerializedName("temperature")
    val temperature: Double?,

    @SerializedName("durationHours")
    val durationHours: Int?,

    @SerializedName("comments")
    val comments: String?,

    @SerializedName("isCompleted")
    val isCompleted: Boolean?
)
