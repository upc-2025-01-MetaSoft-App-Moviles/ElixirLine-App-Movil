package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


/*
 stage = null,
 registeredBy = null,
 startDate = null,
 endDate = null,
 method = null, // Ej. 'Bentonita'
 clarifyingAgents = [], // Array de { name, dose }
 turbidityBeforeNTU = null,
 turbidityAfterNTU = null,
 volumeLiters = null,
 temperature = null,
 durationHours = null,
 comments = null,
 isCompleted = false
*/

class ClarificationStageResponse (

    @SerializedName( "stage")
    val stage: String,
    @SerializedName( "registeredBy")
    val registeredBy: String,
    @SerializedName( "startDate")
    val startDate: String,
    @SerializedName( "endDate")
    val endDate: String,
    @SerializedName( "method")
    val method: String, // Ej. 'Bentonita'
    @SerializedName( "clarifyingAgents")
    val clarifyingAgents: List<ClarifyingAgentEntity>,
    @SerializedName( "turbidityBeforeNTU")
    val turbidityBeforeNTU: Double?,
    @SerializedName( "turbidityAfterNTU")
    val turbidityAfterNTU: Double?,
    @SerializedName( "volumeLiters")
    val volumeLiters: Double?,
    @SerializedName( "temperature")
    val temperature: Double?,
    @SerializedName( "durationHours")
    val durationHours: Int?,
    @SerializedName( "comments")
    val comments: String?,
    @SerializedName( "isCompleted")
    val isCompleted: Boolean

)