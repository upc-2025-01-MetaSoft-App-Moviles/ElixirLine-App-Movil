package com.metasoft.elixirline_app_movil.fieldworkersmanagement.data.local.worker

data class WorkerEntity(
    val id: Int = 0,
    val nombre: String,
    val dni: String,
    val celular: String,
    val rol: String,
    val fechaInicio: String,
    val activo: Boolean
)
