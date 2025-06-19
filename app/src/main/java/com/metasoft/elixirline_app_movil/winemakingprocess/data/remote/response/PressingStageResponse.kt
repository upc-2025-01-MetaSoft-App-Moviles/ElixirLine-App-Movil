package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

data class PressingStageResponse (

    @SerializedName("stage")
    val stage: String?,
    @SerializedName("registeredBy")
    val registeredBy: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("pressType")
    val pressType: String?,
    @SerializedName("pressPressureBars")
    val pressPressureBars: Double?,
    @SerializedName("durationMinutes")
    val durationMinutes: Int?,
    @SerializedName("pomaceKg")
    val pomaceKg: Double?,
    @SerializedName("yieldLiters")
    val yieldLiters: Double?,
    @SerializedName("mustUsage")
    val mustUsage: String?,
    @SerializedName("comments")
    val comments: String?,
    @SerializedName("isCompleted")
    val isCompleted: Boolean?
)