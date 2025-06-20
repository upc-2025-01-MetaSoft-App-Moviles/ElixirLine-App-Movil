package com.metasoft.elixirline_app_movil.fieldworkersmanagement.presentation.viewmodel.worker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.fieldworkersmanagement.data.local.worker.WorkerEntity
import kotlinx.coroutines.launch

class WorkerViewModel : ViewModel() {

    // 🔧 Estado para edición (renombrado para evitar conflicto JVM)
    var workerToEdit: WorkerEntity? by mutableStateOf(null)
        private set

    // Lista de trabajadores
    val allWorkers = mutableStateListOf<WorkerEntity>()

    init {
        if (allWorkers.isEmpty()) {
            allWorkers.addAll(
                listOf(
                    WorkerEntity(
                        id = 1,
                        nombre = "Juan Pérez",
                        dni = "12345678",
                        celular = "987654321",
                        rol = "Podador",
                        fechaInicio = "2025-05-17",
                        fechaFinContrato = "2025-12-31",
                        tipoContrato = "Parcial",
                        zonaAsignada = "Viñedo Norte",
                        activo = true
                    ),
                    WorkerEntity(
                        id = 2,
                        nombre = "María López",
                        dni = "87654321",
                        celular = "998877665",
                        rol = "Encargado de Riego",
                        fechaInicio = "2024-03-10",
                        fechaFinContrato = "2025-09-30",
                        tipoContrato = "Temporal",
                        zonaAsignada = "Zona Sur",
                        activo = true
                    ),
                    WorkerEntity(
                        id = 3,
                        nombre = "Carlos Sánchez",
                        dni = "11223344",
                        celular = "912345678",
                        rol = "Cosechador",
                        fechaInicio = "2023-11-01",
                        fechaFinContrato = "2024-11-01",
                        tipoContrato = "Por obra",
                        zonaAsignada = "Viñedo Central",
                        activo = true
                    )
                )
            )
        }
    }

    // 🔁 Insertar o actualizar trabajador
    fun insert(worker: WorkerEntity) {
        val index = allWorkers.indexOfFirst { it.id == worker.id }
        if (index != -1) {
            update(worker)
        } else {
            allWorkers.add(worker.copy(id = (allWorkers.maxOfOrNull { it.id } ?: 0) + 1))
        }
    }

    // 🗑️ Eliminar trabajador
    fun delete(worker: WorkerEntity) {
        viewModelScope.launch {
            allWorkers.removeIf { it.id == worker.id }
        }
    }

    // 🔁 Actualizar datos de un trabajador existente
    fun update(worker: WorkerEntity) {
        val index = allWorkers.indexOfFirst { it.id == worker.id }
        if (index != -1) {
            allWorkers[index] = worker
        }
    }

    // 🖊️ Establecer trabajador a editar
    fun assignWorkerToEdit(worker: WorkerEntity?) {
        workerToEdit = worker
    }
}
