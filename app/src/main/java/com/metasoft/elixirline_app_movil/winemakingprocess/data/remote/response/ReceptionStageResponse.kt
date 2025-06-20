package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa la respuesta de la etapa de prensado en el proceso de vinificación.
 * Esta clase contiene información detallada sobre el prensado, incluyendo tipo de prensa,
 * presión, duración, rendimiento y otros detalles relevantes.
 */

/*
"receptionStage": {
    "stage": "Recepción",
    "registeredBy": "Danni Vargas",
    "startDate": "2025-05-10",
    "endDate": "2025-05-10",
    "sugarLevelBrix": 21.3,
    "pH": 3.28,
    "temperature": 18.7,
    "quantityKg": 1248.4,
    "comments": "Uvas frescas, sin signos de podredumbre.",
    "isCompleted": true
    }
 */

data class ReceptionStageResponse(

    @SerializedName("stage")
    val stage: String?,
    @SerializedName("registeredBy")
    val registeredBy: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("sugarLevelBrix")
    val sugarLevelBrix: Double?,
    @SerializedName("pH")
    val pH: Double?,
    @SerializedName("temperature")
    val temperature: Double?,
    @SerializedName("quantityKg")
    val quantityKg: Double?,
    @SerializedName("comments")
    val comments: String?,
    @SerializedName("isCompleted")
    val isCompleted: Boolean?,
)