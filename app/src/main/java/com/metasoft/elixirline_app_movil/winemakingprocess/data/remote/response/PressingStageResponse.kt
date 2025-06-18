package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


class PressingStageResponse (

    @SerializedName( "stage")
    val stage: String?, // Siempre 'Prensado'
    @SerializedName("registeredBy")
    val registeredBy: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("pressType")
    val pressType: String?, // Ej. 'Neumática', 'Horizontal', etc.
    @SerializedName("pressPressureBars")
    val pressPressureBars: Double, // Presión en bares
    @SerializedName("durationMinutes")
    val durationMinutes: Int, // Duración en minutos
    @SerializedName("pomaceKg")
    val pomaceKg: Double, // Peso de la orujo en kg
    @SerializedName("yieldLiters")
    val yieldLiters: Double, // Rendimiento en litros
    @SerializedName("mustUsage")
    val mustUsage: String?, // Ej. 'Vino blanco joven'
    @SerializedName("comments")
    val comments: String?, // Comentarios adicionales
    @SerializedName("isCompleted")
    val isCompleted: Boolean // Indica si la etapa está completa
)