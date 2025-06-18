package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

class AgingStageResponse (

    @SerializedName( "stage")
    val stage: String,
    @SerializedName( "registeredBy")
    val registeredBy: String,
    @SerializedName( "startDate")
    val startDate: String,
    @SerializedName( "endDate")
    val endDate: String,
    @SerializedName( "containerType")
    val containerType: String,
    @SerializedName( "material")
    val material: String,
    @SerializedName( "containerCode")
    val containerCode: String,
    @SerializedName( "avgTemperature")
    val avgTemperature: Double,
    @SerializedName( "volumeLiters")
    val volumeLiters: Double,
    @SerializedName( "durationMonths")
    val durationMonths: Int,
    @SerializedName( "frequencyDays")
    val frequencyDays: Int,
    @SerializedName( "refilled")
    val refilled: Boolean,
    @SerializedName( "batonnage")
    val batonnage: Boolean,
    @SerializedName( "rackings")
    val rackings: Int,
    @SerializedName( "purpose")
    val purpose: String,
    @SerializedName( "comments")
    val comments: String,
    @SerializedName( "isCompleted")
    val isCompleted: Boolean,

    )