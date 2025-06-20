package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


data class FiltrationStageResponse(

    @SerializedName("stage")
    val stage: String?,

    @SerializedName("registeredBy")
    val registeredBy: String?,

    @SerializedName("startDate")
    val startDate: String?,

    @SerializedName("endDate")
    val endDate: String?,

    @SerializedName("filtrationType")
    val filtrationType: String?,

    @SerializedName("filterMedia")
    val filterMedia: String?,

    @SerializedName("poreMicrons")
    val poreMicrons: Double?,

    @SerializedName("turbidityBefore")
    val turbidityBefore: Double?,

    @SerializedName("turbidityAfter")
    val turbidityAfter: Double?,

    @SerializedName("temperature")
    val temperature: Double?,

    @SerializedName("pressureBars")
    val pressureBars: Double?,

    @SerializedName("filteredVolumeLiters")
    val filteredVolumeLiters: Double?,

    @SerializedName("isSterile")
    val isSterile: Boolean?,

    @SerializedName("filterChanged")
    val filterChanged: Boolean?,

    @SerializedName("changeReason")
    val changeReason: String?,

    @SerializedName("comments")
    val comments: String?,

    @SerializedName("isCompleted")
    val isCompleted: Boolean?
)
