package com.metasoft.elixirline_app_movil.ProductionHistory.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.metasoft.elixirline_app_movil.ProductionHistory.domain.model.ProductionHistory
import com.metasoft.elixirline_app_movil.ProductionHistory.presentation.di.PresentationModule
import com.metasoft.elixirline_app_movil.ProductionHistory.presentation.viewmodel.SeeAllProductionHistoryViewModel
import com.metasoft.elixirline_app_movil.R
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindAllProductionHistoryView() {
    val viewModel = remember { PresentationModule.getProductionHistoryViewModel() }
    val uiState by viewModel.uiState.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }

    var searchText by remember { mutableStateOf("") }
    var searchType by remember { mutableStateOf("recordId") }
    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchError by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.getAllProductionRecords()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo de la aplicación",
                    modifier = Modifier.heightIn(max = 80.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de búsqueda
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                searchError = ""
                            },
                            placeholder = { Text("Buscar por UUID...") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            isError = searchError.isNotEmpty(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Buscar"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                try {
                                    if (searchText.isNotEmpty()) {
                                        val uuid = UUID.fromString(searchText.trim())
                                        when (searchType) {
                                            "recordId" -> viewModel.getProductionRecord(uuid)
                                            "batchId" -> viewModel.getProductionRecordsByBatch(uuid)
                                        }
                                    } else {
                                        searchError = "Ingrese un UUID válido"
                                    }
                                } catch (e: IllegalArgumentException) {
                                    searchError = "UUID inválido. Formato correcto: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                        ) {
                            Text("Buscar")
                        }
                    }

                    if (searchError.isNotEmpty()) {
                        Text(
                            text = searchError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Buscar por: ", fontSize = 14.sp)

                        TextButton(onClick = { isSearchExpanded = true }) {
                            Text(
                                when (searchType) {
                                    "recordId" -> "ID de Registro"
                                    "batchId" -> "ID de Lote"
                                    else -> "ID de Registro"
                                }
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Expandir"
                            )
                        }

                        DropdownMenu(
                            expanded = isSearchExpanded,
                            onDismissRequest = { isSearchExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("ID de Registro") },
                                onClick = {
                                    searchType = "recordId"
                                    isSearchExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("ID de Lote") },
                                onClick = {
                                    searchType = "batchId"
                                    isSearchExpanded = false
                                }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(
                            onClick = {
                                searchText = ""
                                searchError = ""
                                viewModel.getAllProductionRecords()
                            }
                        ) {
                            Text("Limpiar filtros")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Historial de Producción",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (uiState) {
                is SeeAllProductionHistoryViewModel.UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                        Text("Cargando datos...", modifier = Modifier.padding(top = 50.dp))
                    }
                }
                is SeeAllProductionHistoryViewModel.UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${(uiState as SeeAllProductionHistoryViewModel.UiState.Error).message}", color = Color.Red)
                    }
                }
                is SeeAllProductionHistoryViewModel.UiState.Success -> {
                    val data = (uiState as SeeAllProductionHistoryViewModel.UiState.Success).data
                    if (data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No hay registros disponibles")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(data) { item ->
                                ProductionHistoryCard(
                                    productionHistory = item,
                                    onDeleteClick = { recordId ->
                                        viewModel.deleteProductionRecord(recordId)
                                        viewModel.getAllProductionRecords()
                                    }
                                )
                            }
                        }
                    }
                }
                is SeeAllProductionHistoryViewModel.UiState.SuccessSingle -> {
                    val data = (uiState as SeeAllProductionHistoryViewModel.UiState.SuccessSingle).data
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            ProductionHistoryCard(
                                productionHistory = data,
                                onDeleteClick = { recordId ->
                                    viewModel.deleteProductionRecord(recordId)
                                    viewModel.getAllProductionRecords()
                                }
                            )
                        }
                    }
                }
                is SeeAllProductionHistoryViewModel.UiState.SuccessOperation -> {
                    LaunchedEffect(key1 = true) {
                        viewModel.getAllProductionRecords()
                    }
                }
                else -> {  }
            }
        }

        // Botón flotante para crear nuevo registro
        FloatingActionButton(
            onClick = { showCreateDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF8B0000)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar nuevo registro",
                tint = Color.White
            )
        }
    }

    // Diálogo para crear nuevo registro
    if (showCreateDialog) {
        CreateProductionRecordDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { batchId, startDate, endDate, volumeProduced, brix, ph, temperature ->
                viewModel.createProductionRecord(
                    batchId = batchId,
                    startDate = startDate,
                    endDate = endDate,
                    volumeProduced = volumeProduced,
                    brix = brix,
                    ph = ph,
                    temperature = temperature
                )
                showCreateDialog = false
                viewModel.getAllProductionRecords()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductionHistoryCard(
    productionHistory: ProductionHistory,
    onDeleteClick: (UUID) -> Unit
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado con fechas y botón de eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Inicio: ${productionHistory.startDate}",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }

                IconButton(
                    onClick = { showDeleteConfirmation = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar registro",
                        tint = Color(0xFF8B0000)
                    )
                }
            }

            Text(
                text = "Fin: ${productionHistory.endDate}",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ID del lote
            Text(
                text = "Lote: ${productionHistory.batchId}",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Volumen producido
            Text(
                text = "Volumen: ${productionHistory.volumeProduced} litros",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Métricas de calidad
            Text(
                text = "Métricas de calidad:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Brix", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = "${productionHistory.qualityMetrics.Brix}",
                        fontWeight = FontWeight.Medium
                    )
                }
                Column {
                    Text(text = "pH", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = "${productionHistory.qualityMetrics.Ph}",
                        fontWeight = FontWeight.Medium
                    )
                }
                Column {
                    Text(text = "Temperatura", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = "${productionHistory.qualityMetrics.Temperature}°C",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este registro? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteClick(productionHistory.recordId)
                        showDeleteConfirmation = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProductionRecordDialog(
    onDismiss: () -> Unit,
    onConfirm: (UUID, String, String, Float, Float, Float, Float) -> Unit
) {
    var batchIdText by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var volumeProduced by remember { mutableStateOf("") }
    var brix by remember { mutableStateOf("") }
    var ph by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Crear Nuevo Registro",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Formulario
                OutlinedTextField(
                    value = batchIdText,
                    onValueChange = { batchIdText = it },
                    label = { Text("ID del Lote (UUID)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Fecha de Inicio (DD/MM/YYYY)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("Fecha de Fin (DD/MM/YYYY)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = volumeProduced,
                    onValueChange = { volumeProduced = it },
                    label = { Text("Volumen Producido") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = brix,
                    onValueChange = { brix = it },
                    label = { Text("Brix") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ph,
                    onValueChange = { ph = it },
                    label = { Text("pH") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = temperature,
                    onValueChange = { temperature = it },
                    label = { Text("Temperatura") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            try {
                                val batchId = UUID.fromString(batchIdText)
                                val volProduced = volumeProduced.toFloatOrNull() ?: 0f
                                val brixValue = brix.toFloatOrNull() ?: 0f
                                val phValue = ph.toFloatOrNull() ?: 0f
                                val tempValue = temperature.toFloatOrNull() ?: 0f

                                onConfirm(
                                    batchId,
                                    startDate,
                                    endDate,
                                    volProduced,
                                    brixValue,
                                    phValue,
                                    tempValue
                                )
                            } catch (e: Exception) { }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}