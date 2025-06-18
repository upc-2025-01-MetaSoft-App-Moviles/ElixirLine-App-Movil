package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

class FermentationStageResponse (

    @SerializedName("stage")
    val stage: String?,
    @SerializedName("registeredBy")
    val registeredBy: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("yeastUsedMgL")
    val yeastUsedMgL: Double,
    @SerializedName("pH")
    val pH: Double?,
    @SerializedName("initialBrix")
    val initialBrix: Double?,
    @SerializedName("finalBrix")
    val finalBrix: Double?,
    @SerializedName("initialpH")
    val initialpH: Double?,
    @SerializedName("finalpH")
    val finalpH: Double?,
    @SerializedName("temperatureMax")
    val temperatureMax: Double?,
    @SerializedName("temperatureMin")
    val temperatureMin: Double?,
    @SerializedName("fermentationType")
    val fermentationType: String?,
    @SerializedName("tankCode")
    val tankCode: String?,
    @SerializedName("comments")
    val comments: String?,
    @SerializedName("isCompleted")
    val isCompleted: Boolean,

)