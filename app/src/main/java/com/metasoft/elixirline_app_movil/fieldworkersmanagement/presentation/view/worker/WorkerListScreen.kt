package com.metasoft.elixirline_app_movil.fieldworkersmanagement.presentation.view.worker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metasoft.elixirline_app_movil.fieldworkersmanagement.data.local.worker.WorkerEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerListScreen(
    workers: List<WorkerEntity>,
    onAddClick: () -> Unit
) {
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
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(workers) { worker ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(worker.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("Rol: ${worker.rol}", style = MaterialTheme.typography.bodyMedium)
                        Text("DNI: ${worker.dni}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
