package com.metasoft.elixirline_app_movil.fieldworkersmanagement.presentation.viewmodel.worker

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.metasoft.elixirline_app_movil.fieldworkersmanagement.data.local.worker.WorkerEntity

class WorkerViewModel : ViewModel() {

    val allWorkers = mutableStateListOf<WorkerEntity>()

    init {
        // Carga inicial de prueba
        if (allWorkers.isEmpty()) {
            allWorkers.add(
                WorkerEntity(
                    id = 1,
                    nombre = "Juan Pérez",
                    dni = "12345678",
                    celular = "987654321",
                    rol = "Podador",
                    fechaInicio = "2025-05-17",
                    activo = true
                )
            )
        }
    }

    fun insert(worker: WorkerEntity) {
        allWorkers.add(worker.copy(id = allWorkers.size + 1))
    }
}
