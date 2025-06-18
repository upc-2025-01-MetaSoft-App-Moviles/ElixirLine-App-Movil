package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName


class FiltrationStageResponse (

    @SerializedName("stage")
    val stage: String?,
    @SerializedName("registeredBy")
    val registeredBy: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("filtrationType")
    val filtrationType: String?, // Ej. 'Filtración estéril'
    @SerializedName("filterMedia")
    val filterMedia: String?, // Ej. 'Membrana PES'
    @SerializedName("poreMicrons")
    val poreMicrons: Int, // Tamaño de poro en micrones
    @SerializedName("turbidityBefore")
    val turbidityBefore: Double?, // NTU antes de la filtración
    @SerializedName("turbidityAfter")
    val turbidityAfter: Double?, // NTU después de la filtración
    @SerializedName("temperature")
    val temperature: Double?, // Temperatura durante la filtración
    @SerializedName("pressureBars")
    val pressureBars: Double?, // Presión en bares durante la filtración
    @SerializedName("filteredVolumeLiters")
    val filteredVolumeLiters: Double, // Volumen filtrado en litros
    @SerializedName("isSterile")
    val isSterile: Boolean, // Indica si la filtración es estéril
    @SerializedName("filterChanged")
    val filterChanged: Boolean, // Indica si se cambió el filtro
    @SerializedName("changeReason")
    val changeReason: String?, // Motivo del cambio de filtro
    @SerializedName("comments")
    val comments: String?, // Comentarios adicionales
    @SerializedName("isCompleted")
    val isCompleted: Boolean // Indica si la etapa está completa

    )