package com.metasoft.elixirline_app_movil.ProductionHistory.presentation.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metasoft.elixirline_app_movil.R
import com.metasoft.elixirline_app_movil.ProductionHistory.data.di.DataModule
import com.metasoft.elixirline_app_movil.ProductionHistory.data.remote.ApiConstants
import com.metasoft.elixirline_app_movil.ProductionHistory.domain.model.ProductionHistory
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun FindAllProductionHistoryView() {
    val loading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }
    val productionList = remember { mutableStateOf<List<ProductionHistory>>(emptyList()) }

    val coroutineScope = rememberCoroutineScope()
    val repository = remember { DataModule.getProductionHistoryRepository() }

    val uuid = remember {
        UUID.fromString("00000000-0000-0000-0000-000000000000")
    }

    LaunchedEffect(key1 = true) {
        loading.value = true
        error.value = null

        try {
            Log.d("FindAllProductionHistory", "Consultando API con URL base: ${ApiConstants.BASE_URL}")
            Log.d("FindAllProductionHistory", "UUID de consulta: $uuid")

            // Cargar datos
            coroutineScope.launch {
                try {
                    val result = repository.findAllProductionHistory(uuid)
                    loading.value = false
                    productionList.value = result
                    Log.d("FindAllProductionHistory", "Datos recibidos: ${result.size}")
                } catch (e: Exception) {
                    loading.value = false
                    error.value = e.message ?: "Error desconocido"
                    Log.e("FindAllProductionHistory", "Error al cargar datos", e)
                }
            }
        } catch (e: Exception) {
            loading.value = false
            error.value = "Error: ${e.message}"
            Log.e("FindAllProductionHistory", "Error en LaunchedEffect", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Aquí colocamos el logo estático
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

        // Separador entre logo y título
        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Historial de Producción",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            loading.value -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    Text("Cargando datos...", modifier = Modifier.padding(top = 50.dp))
                }
            }
            error.value != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${error.value}", color = Color.Red)
                }
            }
            productionList.value.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay registros disponibles")
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productionList.value) { item ->
                        ProductionHistoryCard(item)
                    }
                }
            }
        }
    }
}


@Composable
fun ProductionHistoryCard(productionHistory: ProductionHistory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Lote: ${productionHistory.batchId}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "ID de Registro: ${productionHistory.recordId}")
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Inicio: ${productionHistory.startDate}")
                Text(text = "Fin: ${productionHistory.endDate}")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Volumen producido: ${productionHistory.volumeProduced}")

            // En lugar de iterar sobre el mapa:
            if (productionHistory.qualityMetrics != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Métricas de calidad:", fontWeight = FontWeight.Bold)

                with(productionHistory.qualityMetrics) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Brix")
                        Text(text = "$Brix")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "pH")
                        Text(text = "$Ph")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Temperatura")
                        Text(text = "$Temperature")
                    }
                }
            }
        }
    }
}
