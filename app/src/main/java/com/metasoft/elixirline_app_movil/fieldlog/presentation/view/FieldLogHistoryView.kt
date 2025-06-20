package com.metasoft.elixirline_app_movil.fieldlog.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.model.FieldLogEntryResponse
import com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel.FieldLogHistoryViewModel

@Composable
fun FieldLogHistoryView(
    viewModel: FieldLogHistoryViewModel,
    navController: NavController
) {
    val entries by viewModel.entries.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadEntries()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Historial de Bitácoras", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
        }

        if (errorMessage != null) {
            Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(entries) { entry ->
                FieldLogEntryItemView(entry)
                Divider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("fieldLogForm") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar incidente")
        }
    }
}

@Composable
fun FieldLogEntryItemView(entry: FieldLogEntryResponse) {
    Column(modifier = Modifier.padding(8.dp)) {
        val fechaCorta = entry.timestamp.take(10)
        val loteCorto = entry.parcelId.take(6)

        Text("Fecha: $fechaCorta")
        Text("Lote: $loteCorto")
        Text("Tipo: ${entry.entryType}")
        Text("Descripción: ${entry.description}")

        val primeraFoto = entry.photoUrls.firstOrNull()
        if (!primeraFoto.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(primeraFoto),
                contentDescription = "Foto del incidente",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
