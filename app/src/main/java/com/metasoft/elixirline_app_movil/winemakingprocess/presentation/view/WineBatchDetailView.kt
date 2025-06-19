package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.AgingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.BottlingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ClarificationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.CorrectionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FermentationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.FiltrationStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.PressingStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.ReceptionStageResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.di.PresentationModuleWinemaking
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel.StagesByWineBatchViewModel
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel.WineBatchesListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WineBatchDetailView(
    wineBatchDetailViewModel: WineBatchesListViewModel = PresentationModuleWinemaking.getWineBatchesListViewModel(),
    stagesByWineBatchViewModel: StagesByWineBatchViewModel = PresentationModuleWinemaking.getStagesByWineBatchViewModel(),
    onBack: () -> Unit = {},
    onAddStageClick: () -> Unit = {},
    batchId: String,
) {

    val wineBatch = wineBatchDetailViewModel.selectedBatch.collectAsState().value
    val stagesByBatchId = stagesByWineBatchViewModel.stagesByBatchId.collectAsState().value

    // === Lanzar la carga solo una vez ===
    LaunchedEffect(batchId) {
        if (wineBatch == null || wineBatch.id != batchId) {
            wineBatchDetailViewModel.getWineBatchById(batchId)
        }
        if (stagesByBatchId.isEmpty() || stagesByBatchId.firstOrNull()?.batchId != batchId) {
            stagesByWineBatchViewModel.getStagesByBatchId(batchId)
        }
    }


    // Log para depuración
    Log.d("WineBatchDetail", "Selected Batch: $wineBatch")
    Log.d("WineBatchDetail", "Stages by Batch ID: $stagesByBatchId")

    // Etapas de vinificación
    val reception = stagesByBatchId.firstOrNull()?.receptionStage
    Log.d("WineBatchDetail", "Reception Stage: $reception")
    val correction = stagesByBatchId.firstOrNull()?.correctionStage
    Log.d("WineBatchDetail", "Correction Stage: $correction")
    val fermentation = stagesByBatchId.firstOrNull()?.fermentationStage
    Log.d("WineBatchDetail", "Fermentation Stage: $fermentation")
    val pressing = stagesByBatchId.firstOrNull()?.pressingStage
    Log.d("WineBatchDetail", "Pressing Stage: $pressing")
    val clarification = stagesByBatchId.firstOrNull()?.clarificationStage
    Log.d("WineBatchDetail", "Clarification Stage: $clarification")
    val aging = stagesByBatchId.firstOrNull()?.agingStage
    Log.d("WineBatchDetail", "Aging Stage: $aging")
    val filtration = stagesByBatchId.firstOrNull()?.filtrationStage
    Log.d("WineBatchDetail", "Filtration Stage: $filtration")
    val bottling = stagesByBatchId.firstOrNull()?.bottlingStage
    Log.d("WineBatchDetail", "Bottling Stage: $bottling")



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de lote") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B0000),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (wineBatch) {
                null -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {

                        item {
                            WineBatchDetailCard(wineBatch)
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Etapas de vinificación",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )

                                FilledTonalButton(
                                    onClick = onAddStageClick,
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Agregar Etapa")
                                }
                            }
                        }

                        // Aquí puedes iterar o mostrar una etapa específica

                        item {
                            if (reception != null) {
                                ReceptionStageCard(reception)
                            }
                        }

                        item {
                            if (correction != null) {
                                CorrectionStageCard(correction)
                            }
                        }

                        item {
                            if (fermentation != null) {
                                FermentationStageCard(fermentation)
                            }
                        }

                        item {
                            if (pressing != null) {
                                PressingStageCard(pressing)
                            }
                        }

                        item {
                            if (clarification != null) {
                                ClarificationStageCard(clarification)
                            }
                        }

                        item {
                            if (aging != null) {
                                AgingStageCard(aging)
                            }
                        }

                        item {
                            if (filtration != null) {
                                FiltrationStageCard(filtration)
                            }
                        }

                        item {
                            if (bottling != null) {
                                BottlingStageCard(bottling)
                            }
                        }

                        // Si no hay etapas, mostrar un mensaje

                        if (stagesByBatchId.isEmpty()) {
                            item {
                                Text(
                                    "No hay etapas de vinificación registradas para este lote.",
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Gray
                                )
                            }
                        }




                    }
                }
            }
        }
    }
}

@Composable
fun ReceptionStageCard(reception: ReceptionStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recepción", fontWeight = FontWeight.Bold)
            Text("📅 Inicio: ${reception.startDate}")
            Text("🍇 Cantidad: ${reception.quantityKg} kg | 🌡️ Temp: ${reception.temperature}°C")
            Text("👤 Registrado por: ${reception.registeredBy}")
            Text(
                if (reception.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun CorrectionStageCard(correction: CorrectionStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Corrección", fontWeight = FontWeight.Bold)
            Text("📅 Inicio: ${correction.startDate}")
            Text("Brix: ${correction.initialBrix} → ${correction.finalBrix}")
            Text("pH: ${correction.initialPH} → ${correction.finalPH}")
            Text("Azúcar añadida: ${correction.addedSugarKg} kg")
            Text("👤 Registrado por: ${correction.registeredBy}")
            Text(
                if (correction.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun FermentationStageCard(stage: FermentationStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Fermentación ${stage.fermentationType ?: ""}", fontWeight = FontWeight.Bold)

            Text(stage.startDate ?: "-")
            Text("Brix: ${stage.initialBrix} → ${stage.finalBrix} | pH: ${stage.initialpH} → ${stage.finalpH}")
            Text("Temp: ${stage.temperatureMin}°C – ${stage.temperatureMax}°C | Levadura: ${stage.yeastUsedMgL} mg/L")
            Text("Tanque: ${stage.tankCode} | ${stage.registeredBy}")
            Text(
                if (stage.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun PressingStageCard(pressing: PressingStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Prensado", fontWeight = FontWeight.Bold)
            Text("📅 Inicio:  ${pressing.startDate}")
            Text("Tipo: ${pressing.pressType} | Presión: ${pressing.pressPressureBars} bar")
            Text("Rendimiento: ${pressing.yieldLiters} L | Orujo: ${pressing.pomaceKg} kg")
            Text("👤Registrado por: ${pressing.registeredBy}")
            Text(
                if (pressing.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ClarificationStageCard(clarification: ClarificationStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Clarificación", fontWeight = FontWeight.Bold)
            Text("📅 ${clarification.startDate}")
            Text("Método: ${clarification.method} | Temp: ${clarification.temperature}°C")
            Text("Turbidez: ${clarification.turbidityBeforeNTU} → ${clarification.turbidityAfterNTU} NTU")
            Text("Volumen: ${clarification.volumeLiters} L")
            Text("👤 Registrado por: ${clarification.registeredBy}")
            Text(
                if (clarification.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun AgingStageCard(aging: AgingStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Añejamiento", fontWeight = FontWeight.Bold)
            Text("📅 ${aging.startDate}")
            Text("Contenedor: ${aging.containerType} (${aging.material})")
            Text("Volumen: ${aging.volumeLiters} L | Tiempo: ${aging.durationMonths} meses")
            Text("👤 Registrado por: ${aging.registeredBy}")
            Text(
                if (aging.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun FiltrationStageCard(filtration: FiltrationStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Filtración", fontWeight = FontWeight.Bold)
            Text("📅 ${filtration.startDate}")
            Text("Tipo: ${filtration.filtrationType} | Medio: ${filtration.filterMedia}")
            Text("Turbidez: ${filtration.turbidityBefore} → ${filtration.turbidityAfter} NTU")
            Text("Volumen: ${filtration.filteredVolumeLiters} L | Temp: ${filtration.temperature}°C")
            Text("👤 Registrado por: ${filtration.registeredBy}")
            Text(
                if (filtration.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun BottlingStageCard(bottling: BottlingStageResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Embotellado", fontWeight = FontWeight.Bold)
            Text("📅 ${bottling.startDate}")
            Text("Línea: ${bottling.bottlingLine} | Botellas: ${bottling.bottlesFilled}")
            Text("Volumen total: ${bottling.totalVolumeLiters} L (${bottling.bottleVolumeMl} ml por botella)")
            Text("Tipo de sello: ${bottling.sealType}")
            Text("👤 Registrado por: ${bottling.registeredBy}")
            Text(
                if (bottling.isCompleted == true) "✔ Completado"
                else "❌ No completado",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun WineBatchDetailCard(wineBatch: WineBatchResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = wineBatch.urlImage,
                contentDescription = "Wine Batch Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            InfoRow(label = "Creado por:", value = wineBatch.createdBy)
            InfoRow(label = "Código Interno:", value = wineBatch.internalCode)
            InfoRow(label = "Campaña de Cosecha:", value = wineBatch.harvestCampaign)
            InfoRow(label = "Viñedo de Origen:", value = wineBatch.vineyardOrigin)
            InfoRow(label = "Variedad de Uva:", value = wineBatch.grapeVariety)
            InfoRow(label = "Cantidad Inicial de Uva (kg):", value = wineBatch.initialGrapeQuantityKg.toString())
            InfoRow(label = "Estado:", value = wineBatch.status)
            InfoRow(label = "Etapa Actual:", value = wineBatch.currentStage)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text("$label ", fontWeight = FontWeight.Bold,)
        Text(value)
    }
}