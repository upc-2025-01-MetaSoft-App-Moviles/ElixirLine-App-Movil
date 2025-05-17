
package com.metasoft.elixirline_app_movil.supplies.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metasoft.elixirline_app_movil.R
import com.metasoft.elixirline_app_movil.supplies.presentation.di.PresentationModule
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyUsageViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplyUsageView(
    viewModel: SupplyUsageViewModel = PresentationModule.getSupplyUsageViewModel(),
    preselectedSupplyId: UUID? = null,
    onSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val supplies by viewModel.supplies.collectAsState()
    val selectedSupply by viewModel.selectedSupply.collectAsState()

    val quantity by viewModel.quantity.collectAsState()
    val activity by viewModel.activity.collectAsState()
    val date by viewModel.date.collectAsState()
    val operatorName by viewModel.operatorName.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var expandedSupply by remember { mutableStateOf(false) }
    var expandedActivity by remember { mutableStateOf(false) }

    // Actividades disponibles
    val activities = listOf("Fertilización", "Fumigación", "Riego", "Poda", "Cosecha", "Otro")

    LaunchedEffect(preselectedSupplyId) {
        if (preselectedSupplyId != null) {
            viewModel.selectSupply(preselectedSupplyId)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is SupplyUsageViewModel.UiState.Success -> {
                snackbarHostState.showSnackbar("Uso de insumo registrado correctamente")
                onSuccess()
            }
            is SupplyUsageViewModel.UiState.Error -> {
                snackbarHostState.showSnackbar((uiState as SupplyUsageViewModel.UiState.Error).message)
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Uso") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Dropdown para seleccionar insumo
                ExposedDropdownMenuBox(
                    expanded = expandedSupply,
                    onExpandedChange = { expandedSupply = it }
                ) {
                    OutlinedTextField(
                        value = selectedSupply?.name ?: "",
                        onValueChange = { },
                        label = { Text("Insumo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSupply) },
                        isError = selectedSupply == null
                    )

                    ExposedDropdownMenu(
                        expanded = expandedSupply,
                        onDismissRequest = { expandedSupply = false }
                    ) {
                        supplies.forEach { supply ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(supply.name)
                                        Text(
                                            text = "Disponible: ${supply.quantity} ${supply.unit}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                },
                                onClick = {
                                    viewModel.selectSupply(supply.id)
                                    expandedSupply = false
                                }
                            )
                        }
                    }
                }

                if (selectedSupply != null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Categoría",
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = selectedSupply!!.category,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8B0000)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Disponible",
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${selectedSupply!!.quantity} ${selectedSupply!!.unit}",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { viewModel.quantity.value = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = quantity.isEmpty()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown para actividad
                ExposedDropdownMenuBox(
                    expanded = expandedActivity,
                    onExpandedChange = { expandedActivity = it }
                ) {
                    OutlinedTextField(
                        value = activity,
                        onValueChange = { viewModel.activity.value = it },
                        label = { Text("Actividad") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedActivity) },
                        isError = activity.isEmpty()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedActivity,
                        onDismissRequest = { expandedActivity = false }
                    ) {
                        activities.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    viewModel.activity.value = option
                                    expandedActivity = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { viewModel.date.value = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = operatorName,
                    onValueChange = { viewModel.operatorName.value = it },
                    label = { Text("Operario") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.registerUsage() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    enabled = viewModel.validateForm()
                ) {
                    Text("REGISTRAR", modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            if (uiState is SupplyUsageViewModel.UiState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF8B0000))
                }
            }
        }
    }
}
