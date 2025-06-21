package com.metasoft.elixirline_app_movil.fieldworkersmanagement.presentation.view.worker

import android.util.Base64
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.metasoft.elixirline_app_movil.fieldworkersmanagement.data.local.worker.WorkerEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerListScreen(
    workers: List<WorkerEntity>,
    onAddClick: () -> Unit,
    onDelete: (WorkerEntity) -> Unit,
    onEdit: (WorkerEntity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var workerToDelete by remember { mutableStateOf<WorkerEntity?>(null) }
    var isDialogActive by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val visibleWorkers = remember { mutableStateMapOf<Int, Boolean>() }

    val filteredWorkers = workers.filter {
        it.nombre.contains(searchQuery, ignoreCase = true) ||
                it.rol.contains(searchQuery, ignoreCase = true) ||
                it.dni.contains(searchQuery, ignoreCase = true)
    }

    val groupedWorkers = filteredWorkers.groupBy { it.rol }

    LaunchedEffect(workers) {
        workers.forEach { worker ->
            if (worker.id !in visibleWorkers) visibleWorkers[worker.id] = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Trabajadores") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF880000),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick, containerColor = Color(0xFF880000)) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar trabajador...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            LazyColumn {
                groupedWorkers.forEach { (rol, workersByRol) ->
                    item {
                        Text(
                            text = "Rol: $rol",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(workersByRol.sortedBy { it.nombre }, key = { it.id }) { worker ->
                        val isVisible = visibleWorkers[worker.id] ?: true

                        AnimatedVisibility(
                            visible = isVisible,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Row(modifier = Modifier.padding(16.dp)) {
                                        if (!worker.fotoPerfil.isNullOrBlank()) {
                                            if (worker.fotoPerfil.startsWith("data:image")) {
                                                val base64Data = worker.fotoPerfil.substringAfter(",")
                                                val imageBytes = Base64.decode(base64Data, Base64.DEFAULT)
                                                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                                bitmap?.let {
                                                    Image(
                                                        bitmap = it.asImageBitmap(),
                                                        contentDescription = "Foto de perfil",
                                                        modifier = Modifier
                                                            .size(56.dp)
                                                            .padding(end = 12.dp),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                            } else {
                                                AsyncImage(
                                                    model = worker.fotoPerfil,
                                                    contentDescription = "Foto de perfil",
                                                    modifier = Modifier
                                                        .size(56.dp)
                                                        .padding(end = 12.dp),
                                                    contentScale = ContentScale.Crop
                                                )
                                            }
                                        }

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(worker.nombre, style = MaterialTheme.typography.titleMedium)
                                            Text("DNI: ${worker.dni}", style = MaterialTheme.typography.bodySmall)
                                            Text("Contrato: ${worker.tipoContrato}", style = MaterialTheme.typography.bodySmall)
                                            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                                            val fechaInicioFormatted = try {
                                                val parsed = inputFormat.parse(worker.fechaInicio)
                                                "Inicio: ${outputFormat.format(parsed ?: Date())}"
                                            } catch (e: Exception) {
                                                "Inicio: ${worker.fechaInicio}"
                                            }

                                            val fechaFinFormatted = try {
                                                val parsed = inputFormat.parse(worker.fechaFinContrato)
                                                "Fin: ${outputFormat.format(parsed ?: Date())}"
                                            } catch (e: Exception) {
                                                "Fin: ${worker.fechaFinContrato}"
                                            }

                                            Text(fechaInicioFormatted, style = MaterialTheme.typography.bodySmall)
                                            Text(fechaFinFormatted, style = MaterialTheme.typography.bodySmall)

                                            val diasRestantes = try {
                                                val hoy = Calendar.getInstance().time
                                                val fechaFin = inputFormat.parse(worker.fechaFinContrato)
                                                val diffMillis = (fechaFin?.time ?: 0) - hoy.time
                                                (diffMillis / (1000 * 60 * 60 * 24)).toInt()
                                            } catch (e: Exception) {
                                                Int.MAX_VALUE
                                            }

                                            if (diasRestantes in 0..7) {
                                                AssistChip(
                                                    onClick = {},
                                                    label = { Text("Contrato por vencer") },
                                                    colors = AssistChipDefaults.assistChipColors(
                                                        containerColor = Color(0xFFFFCDD2),
                                                        labelColor = Color(0xFFD32F2F)
                                                    )
                                                )
                                            }
                                        }

                                        Column(modifier = Modifier.align(Alignment.Top)) {
                                            IconButton(
                                                onClick = { onEdit(worker) },
                                                modifier = Modifier.padding(4.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Edit,
                                                    contentDescription = "Editar",
                                                    tint = Color(0xFF00695C)
                                                )
                                            }
                                            IconButton(
                                                onClick = {
                                                    if (!isDialogActive) {
                                                        workerToDelete = worker
                                                        showDialog = true
                                                        isDialogActive = true
                                                    }
                                                },
                                                enabled = !isDialogActive
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Eliminar",
                                                    tint = Color(0xFFB00020)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog && workerToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                isDialogActive = false
            },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar a ${workerToDelete?.nombre}?") },
            confirmButton = {
                TextButton(onClick = {
                    val deleted = workerToDelete
                    showDialog = false
                    isDialogActive = false

                    deleted?.let { worker ->
                        visibleWorkers[worker.id] = false
                        scope.launch {
                            delay(300)
                            onDelete(worker)
                            snackbarHostState.showSnackbar("Trabajador eliminado correctamente")
                        }
                    }
                }) {
                    Text("Sí, eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    isDialogActive = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
