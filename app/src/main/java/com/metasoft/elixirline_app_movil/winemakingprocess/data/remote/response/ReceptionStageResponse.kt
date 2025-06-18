package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa la respuesta de la etapa de prensado en el proceso de vinificación.
 * Esta clase contiene información detallada sobre el prensado, incluyendo tipo de prensa,
 * presión, duración, rendimiento y otros detalles relevantes.
 */
class ReceptionStageResponse(

    @SerializedName("stage")
    val stage: String?, // Siempre 'Recepción'
    @SerializedName("registeredBy")
    val registeredBy: String?, // Usuario que registra la etapa
    @SerializedName("startDate")
    val startDate: String?, // Fecha de inicio de la etapa
    @SerializedName("endDate")
    val endDate: String?, // Fecha de finalización de la etapa
    @SerializedName("sugarLevelBrix")
    val sugarLevelBrix: Double?, // Nivel de azúcar en Brix
    @SerializedName("pH")
    val pH: Double?, // pH del mosto
    @SerializedName("temperature")
    val temperature: Double?, // Temperatura del mosto
    @SerializedName("quantityKg")
    val quantityKg: Double?, // Cantidad de uvas en kg
    @SerializedName("comments")
    val comments: String?, // Comentarios adicionales
    @SerializedName("isCompleted")
    val isCompleted: Boolean // Indica si la etapa está completa

)