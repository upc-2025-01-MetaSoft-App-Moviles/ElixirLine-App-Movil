package com.metasoft.elixirline_app_movil.supplies.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metasoft.elixirline_app_movil.R
import com.metasoft.elixirline_app_movil.supplies.domain.model.Supply
import com.metasoft.elixirline_app_movil.supplies.presentation.di.PresentationModule
import com.metasoft.elixirline_app_movil.supplies.presentation.viewmodel.SupplyListViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplyListView(
    viewModel: SupplyListViewModel = PresentationModule.getSupplyListViewModel(),
    onAddClick: () -> Unit,
    onItemClick: (UUID) -> Unit,
    onRegisterUsageClick: (UUID) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredSupplies by viewModel.filteredSupplies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de insumos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B0000),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar insumo")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

            // Search bar - Using OutlinedTextField instead of TextField with textFieldColors
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search") },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )

            when (uiState) {
                is SupplyListViewModel.UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF8B0000))
                    }
                }
                is SupplyListViewModel.UiState.Success -> {
                    if (filteredSupplies.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No hay insumos registrados")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredSupplies) { supply ->
                                SupplyItem(
                                    supply = supply,
                                    onEditClick = { onItemClick(supply.id) },
                                    onInfoClick = { onItemClick(supply.id) },
                                    onRegisterUsageClick = { onRegisterUsageClick(supply.id) }
                                )
                            }
                        }
                    }
                }
                is SupplyListViewModel.UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (uiState as SupplyListViewModel.UiState.Error).message,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SupplyItem(
    supply: Supply,
    onEditClick: () -> Unit,
    onInfoClick: () -> Unit,
    onRegisterUsageClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = supply.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Fecha: ${supply.expirationDate}",
                fontSize = 14.sp
            )

            Text(
                text = "Cantidad: ${supply.quantity} ${supply.unit}",
                fontSize = 14.sp
            )

            Text(
                text = supply.category,
                fontSize = 14.sp,
                color = Color(0xFF8B0000)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF8B0000)
                    )
                }

                IconButton(
                    onClick = onInfoClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = Color(0xFF8B0000)
                    )
                }

                IconButton(
                    onClick = onRegisterUsageClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Registrar uso",
                        tint = Color(0xFF8B0000)
                    )
                }
            }
        }
    }
}
