package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.di.PresentationModuleWinemaking
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel.WineBatchesListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WineBatchesListView(
    wineBatchesListViewModel: WineBatchesListViewModel = PresentationModuleWinemaking.getWineBatchesListViewModel(),
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onStageClick: (WineBatchResponse) -> Unit,
    onEditClick: (WineBatchResponse) -> Unit,
    onInfoClick: (WineBatchResponse) -> Unit,
) {

    // Obtener los lotes de vino
    wineBatchesListViewModel.getWineBatches()

    // Estado para los lotes de vino
    val wineBatches = wineBatchesListViewModel.wineBatches.collectAsState()

    // Estado para la barra de búsqueda
    val searchQuery = remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de lotes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8B0000), titleContentColor = Color.White)
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar lote")
            }
        },

        floatingActionButtonPosition = FabPosition.End,

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Search bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Search Batch") },
                    singleLine = true,
                    trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
            }

            // Lista de lotes
            LazyColumn {
                items(wineBatches.value.filter {
                    it.internalCode.contains(searchQuery.value, ignoreCase = true)
                }) { batch ->
                    WineBatchCard(
                        batch = batch,
                        onStageClick = onStageClick,
                        onEditClick = onEditClick,
                        onInfoClick = onInfoClick
                    )
                }
            }

            // Mensaje si no hay lotes
            if (wineBatches.value.isEmpty()) {
                Text(
                    text = "No hay lotes de vino registrados",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mensaje si no hay resultados

            if (searchQuery.value.isNotEmpty() && wineBatches.value.none {
                    it.internalCode.contains(searchQuery.value, ignoreCase = true)
                }) {
                Text(
                    text = "No se encontraron resultados para '${searchQuery.value}'",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }
}



@Composable
fun WineBatchCard(
    batch: WineBatchResponse,
    onStageClick: (WineBatchResponse) -> Unit,
    onEditClick: (WineBatchResponse) -> Unit,
    onInfoClick: (WineBatchResponse) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color(0xFF8B0000))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(batch.internalCode, fontWeight = FontWeight.Bold)
            Text("In Progress", color = Color(0xFFFFA500)) // Naranja
            Spacer(Modifier.height(4.dp))
            Text("Fecha: ${batch.receptionDate}")
            Text("Etapa actual: ${batch.currentStage}", fontWeight = FontWeight.Medium)
            Text("Tipo de uva: ${batch.grapeVariety}")

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Button(onClick = { onStageClick(batch) }) { Text("Etapa") }
                //Spacer(modifier = Modifier.width(8.dp)) // Espacio entre botones
                OutlinedButton(onClick = { onEditClick(batch) }) { Text("Editar") }
                //Spacer(modifier = Modifier.width(8.dp)) // Espacio entre botones
                OutlinedButton(onClick = { onInfoClick(batch) }) { Text("Info") }
            }
        }
    }
}

