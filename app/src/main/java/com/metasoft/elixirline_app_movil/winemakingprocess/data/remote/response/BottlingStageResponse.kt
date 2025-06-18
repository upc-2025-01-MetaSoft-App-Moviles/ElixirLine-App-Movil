package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

class BottlingStageResponse (

    @SerializedName( "stage")
    val stage: String?,
    @SerializedName( "registeredBy")
    val registeredBy: String?,
    @SerializedName( "startDate")
    val startDate: String?,
    @SerializedName( "endDate")
    val endDate: String?,
    @SerializedName( "bottlingLine")
    val bottlingLine: String?, // Línea de embotellado
    @SerializedName( "bottlesFilled")
    val bottlesFilled: Int,               // Total de botellas llenadas
    @SerializedName( "bottleVolumeMl")
    val bottleVolumeMl: Int,             // Volumen por botella
    @SerializedName( "totalVolumeLiters")
    val totalVolumeLiters: Double,       // Volumen total embotellado
    @SerializedName( "sealType")
    val sealType: String?,                         // Tipo de sellado (ej. 'Corcho natural')
    @SerializedName( "code")
    val code: String?,                                 // Código identificador del lote embotellado
    @SerializedName( "temperature")
    val temperature: Double?,                   // Temperatura durante embotellado
    @SerializedName( "wasFiltered")
    val wasFiltered: Boolean = false,
    @SerializedName( "wereLabelsApplied")
    val wereLabelsApplied: Boolean = false,
    @SerializedName( "wereCapsulesApplied")
    val wereCapsulesApplied: Boolean = false,
    @SerializedName( "comments")
    val comments: String,
    @SerializedName( "isCompleted")
    val isCompleted: Boolean = false
)


