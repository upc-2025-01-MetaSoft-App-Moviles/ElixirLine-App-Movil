package com.metasoft.elixirline_app_movil.supplies.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.metasoft.elixirline_app_movil.R
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.presentation.di.PresentationModule
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyDetailViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplyDetailView(
    supplyId: UUID,
    viewModel: SupplyDetailViewModel = PresentationModule.getSupplyDetailViewModel(),
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val name by viewModel.name.collectAsState()
    val category by viewModel.category.collectAsState()
    val quantity by viewModel.quantity.collectAsState()
    val unit by viewModel.unit.collectAsState()
    val location by viewModel.location.collectAsState()
    val expirationDate by viewModel.expirationDate.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // Categorías disponibles
    val categories = listOf("Fertilizante", "Pesticida", "Herramientas", "Maquinaria", "Otro")
    var expandedCategory by remember { mutableStateOf(false) }

    // Unidades disponibles
    val units = listOf("kg", "lt", "unidades", "pares", "cajas")
    var expandedUnit by remember { mutableStateOf(false) }

    // Ubicaciones disponibles
    val locations = listOf("Bodega Principal", "Almacén", "Campo", "Otro")
    var expandedLocation by remember { mutableStateOf(false) }

    LaunchedEffect(supplyId) {
        viewModel.loadSupply(supplyId)
    }

    LaunchedEffect(uiState) {
        if (uiState is SupplyDetailViewModel.UiState.Success) {
            val supply = (uiState as SupplyDetailViewModel.UiState.Success).supply
            if (supply.id == supplyId) {
                // No hacer nada, ya está cargado
            } else {
                snackbarHostState.showSnackbar("Insumo actualizado correctamente")
                onSuccess()
            }
        } else if (uiState is SupplyDetailViewModel.UiState.Error) {
            snackbarHostState.showSnackbar((uiState as SupplyDetailViewModel.UiState.Error).message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Insumo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B0000),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo de la aplicación",
                    modifier = Modifier.height(60.dp)
                )
            }

            when (uiState) {
                is SupplyDetailViewModel.UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF8B0000))
                    }
                }
                is SupplyDetailViewModel.UiState.Success -> {
                    val supply = (uiState as SupplyDetailViewModel.UiState.Success).supply

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Categoría",
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = supply.category,
                            color = Color(0xFF8B0000),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutlinedTextField(
                            value = name,
                            onValueChange = { viewModel.name.value = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = name.isEmpty()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Dropdown para categoría
                        ExposedDropdownMenuBox(
                            expanded = expandedCategory,
                            onExpandedChange = { expandedCategory = it }
                        ) {
                            OutlinedTextField(
                                value = category,
                                onValueChange = { viewModel.category.value = it },
                                label = { Text("Categoría") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                                isError = category.isEmpty()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedCategory,
                                onDismissRequest = { expandedCategory = false }
                            ) {
                                categories.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            viewModel.category.value = option
                                            expandedCategory = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = quantity,
                            onValueChange = { viewModel.quantity.value = it },
                            label = { Text("Cantidad") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = quantity.isEmpty()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Dropdown para unidad
                        ExposedDropdownMenuBox(
                            expanded = expandedUnit,
                            onExpandedChange = { expandedUnit = it }
                        ) {
                            OutlinedTextField(
                                value = unit,
                                onValueChange = { viewModel.unit.value = it },
                                label = { Text("Unidad") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedUnit) },
                                isError = unit.isEmpty()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedUnit,
                                onDismissRequest = { expandedUnit = false }
                            ) {
                                units.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            viewModel.unit.value = option
                                            expandedUnit = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Dropdown para ubicación
                        ExposedDropdownMenuBox(
                            expanded = expandedLocation,
                            onExpandedChange = { expandedLocation = it }
                        ) {
                            OutlinedTextField(
                                value = location,
                                onValueChange = { viewModel.location.value = it },
                                label = { Text("Ubicación") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLocation) }
                            )

                            ExposedDropdownMenu(
                                expanded = expandedLocation,
                                onDismissRequest = { expandedLocation = false }
                            ) {
                                locations.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            viewModel.location.value = option
                                            expandedLocation = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = expirationDate,
                            onValueChange = { viewModel.expirationDate.value = it },
                            label = { Text("Fecha de Vencimiento") },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("DD/MM/AAAA") }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.updateSupply() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                            enabled = viewModel.validateForm()
                        ) {
                            Text("GUARDAR CAMBIOS", modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
                is SupplyDetailViewModel.UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (uiState as SupplyDetailViewModel.UiState.Error).message,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}
