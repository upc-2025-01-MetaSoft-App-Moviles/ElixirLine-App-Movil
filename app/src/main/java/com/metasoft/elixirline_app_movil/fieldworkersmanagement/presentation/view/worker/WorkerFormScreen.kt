package com.metasoft.elixirline_app_movil.fieldworkersmanagement.presentation.view.worker

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.metasoft.elixirline_app_movil.fieldworkersmanagement.data.local.worker.WorkerEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerFormScreen(
    workerToEdit: WorkerEntity? = null,
    onSave: (WorkerEntity) -> Unit,
    onCancel: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf(workerToEdit?.nombre ?: "") }
    var dni by remember { mutableStateOf(workerToEdit?.dni ?: "") }
    var rol by remember { mutableStateOf(workerToEdit?.rol ?: "") }
    var fechaInicio by remember { mutableStateOf(workerToEdit?.fechaInicio ?: "") }
    var fechaFinContrato by remember { mutableStateOf(workerToEdit?.fechaFinContrato ?: "") }
    var tipoContrato by remember { mutableStateOf(workerToEdit?.tipoContrato ?: "") }
    var zonaAsignada by remember { mutableStateOf(workerToEdit?.zonaAsignada ?: "") }
    var fotoPerfil by remember { mutableStateOf(workerToEdit?.fotoPerfil ?: "") }
    var nivelExperiencia by remember { mutableStateOf(workerToEdit?.nivelExperiencia ?: "") }

    var expandedRol by remember { mutableStateOf(false) }
    var expandedTipoContrato by remember { mutableStateOf(false) }
    var expandedNivelExp by remember { mutableStateOf(false) }

    val roles = listOf(
        "Podador", "Cosechador", "Encargado de Riego", "Supervisor de Campo",
        "Especialista en Plagas", "Operador de Tractor", "Control de Calidad", "Técnico de Suelo"
    )

    val tiposContrato = listOf("Permanente", "Temporal", "Por obra", "Prácticas")

    val nivelesExperiencia = listOf(
        "Sin experiencia", "1-2 años", "3-5 años", "Más de 5 años", "Técnico especializado", "Ingeniero Agrónomo"
    )

    val showDatePicker: (String, (String) -> Unit) -> Unit = { currentValue, onDateSelected ->
        val calendar = Calendar.getInstance()
        try {
            val parsed = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentValue)
            calendar.time = parsed ?: Date()
        } catch (_: Exception) {}

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selected = Calendar.getInstance()
                selected.set(year, month, dayOfMonth)
                val formatted = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(selected.time)
                onDateSelected(formatted)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = if (workerToEdit == null) "Nuevo Trabajador" else "Editar Trabajador", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = dni,
                onValueChange = { dni = it },
                label = { Text("DNI") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(expanded = expandedRol, onExpandedChange = { expandedRol = !expandedRol }) {
                OutlinedTextField(
                    value = rol,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Rol") },
                    trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedRol, onDismissRequest = { expandedRol = false }) {
                    roles.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role) },
                            onClick = {
                                rol = role
                                expandedRol = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = {},
                label = { Text("Fecha de inicio") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        showDatePicker(fechaInicio) { fechaInicio = it }
                    }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Seleccionar fecha")
                    }
                }
            )

            OutlinedTextField(
                value = fechaFinContrato,
                onValueChange = {},
                label = { Text("Fin de contrato") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        showDatePicker(fechaFinContrato) { fechaFinContrato = it }
                    }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Seleccionar fecha")
                    }
                }
            )

            ExposedDropdownMenuBox(
                expanded = expandedTipoContrato,
                onExpandedChange = { expandedTipoContrato = !expandedTipoContrato }
            ) {
                OutlinedTextField(
                    value = tipoContrato,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de contrato") },
                    trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedTipoContrato, onDismissRequest = { expandedTipoContrato = false }) {
                    tiposContrato.forEach { contrato ->
                        DropdownMenuItem(
                            text = { Text(contrato) },
                            onClick = {
                                tipoContrato = contrato
                                expandedTipoContrato = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = zonaAsignada,
                onValueChange = { zonaAsignada = it },
                label = { Text("Zona o campo asignado") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expandedNivelExp,
                onExpandedChange = { expandedNivelExp = !expandedNivelExp }
            ) {
                OutlinedTextField(
                    value = nivelExperiencia,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Nivel de experiencia / formación") },
                    trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedNivelExp, onDismissRequest = { expandedNivelExp = false }) {
                    nivelesExperiencia.forEach { nivel ->
                        DropdownMenuItem(
                            text = { Text(nivel) },
                            onClick = {
                                nivelExperiencia = nivel
                                expandedNivelExp = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = fotoPerfil,
                onValueChange = { fotoPerfil = it },
                label = { Text("Foto de perfil (URI o base64)") },
                modifier = Modifier.fillMaxWidth()
            )

            if (fotoPerfil.isNotBlank()) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Vista previa",
                    modifier = Modifier
                        .size(80.dp)
                        .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        val entity = WorkerEntity(
                            id = workerToEdit?.id ?: 0,
                            nombre = nombre,
                            dni = dni,
                            rol = rol,
                            fechaInicio = formatFecha(fechaInicio),
                            fechaFinContrato = formatFecha(fechaFinContrato),
                            celular = workerToEdit?.celular ?: "",
                            activo = workerToEdit?.activo ?: true,
                            fotoPerfil = fotoPerfil,
                            tipoContrato = tipoContrato,
                            zonaAsignada = zonaAsignada,
                            nivelExperiencia = nivelExperiencia
                        )
                        onSave(entity)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = if (workerToEdit == null) "Trabajador creado" else "Trabajador actualizado",
                                duration = SnackbarDuration.Long
                            )
                        }
                    },
                    enabled = nombre.isNotBlank() && dni.isNotBlank() && rol.isNotBlank()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}

fun formatFecha(input: String): String {
    return try {
        val parser = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsed = parser.parse(input)
        formatter.format(parsed!!)
    } catch (e: Exception) {
        input
    }
}
