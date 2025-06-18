package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view

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
import androidx.compose.runtime.getValue
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
    onBack : () -> Unit = {},
    onAddStageClick: () -> Unit = {},
    batchId: String,
)
{

    // Obtener el lote de vino por ID ========================
    LaunchedEffect(batchId) {
        wineBatchDetailViewModel.getWineBatchById(batchId)
    }

    val wineBatch by wineBatchDetailViewModel.selectedBatch.collectAsState()

    // Obtener eatpas de vinificación por lote de vino ========================
    LaunchedEffect(batchId) {
        stagesByWineBatchViewModel.getReceptionStageByBatchId(batchId)
        stagesByWineBatchViewModel.getCorrectionStageByBatchId(batchId)
        stagesByWineBatchViewModel.getFermentationStageByBatchId(batchId)
        stagesByWineBatchViewModel.getPressingStageByBatchId(batchId)
        stagesByWineBatchViewModel.getClarificationStageByBatchId(batchId)
        stagesByWineBatchViewModel.getAgingStageByBatchId(batchId)
        stagesByWineBatchViewModel.getFiltrationStageByBatchId(batchId)
        stagesByWineBatchViewModel.getBottlingStageByBatchId(batchId)
    }

    val receptionStage by stagesByWineBatchViewModel.receptionStage.collectAsState()
    val correctionStage by stagesByWineBatchViewModel.correctionStage.collectAsState()
    val fermentationStage by stagesByWineBatchViewModel.fermentationStage.collectAsState()
    val pressingStage by stagesByWineBatchViewModel.pressingStage.collectAsState()
    val clarificationStage by stagesByWineBatchViewModel.clarificationStage.collectAsState()
    val agingStage by stagesByWineBatchViewModel.agingStage.collectAsState()
    val filtrationStage by stagesByWineBatchViewModel.filtrationStage.collectAsState()
    val bottlingStage by stagesByWineBatchViewModel.bottlingStage.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de lote") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8B0000), titleContentColor = Color.White)
            )
        },
    ) {  paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val batch = wineBatch) {
                null -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {

                        // Detalle de lote de vino =========================
                        WineBatchDetailCard(batch)

                        // Título + botón =============================
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

                        // Etapa de recepción =========================
                        if (receptionStage != null) {
                            ReceptionStageCard(receptionStage!!)
                        }

                        // Etapa de corrección =========================








                    }

                }
            }
        }
    }
}

@Composable
fun ReceptionStageCard(reception: ReceptionStageResponse){
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
            Text("Recepción de Uva", fontWeight = FontWeight.Bold)

            InfoRow(label = "Fecha de Recepción:", value = reception.startDate.toString())
            InfoRow(label = "Cantidad de Uva (kg):", value = reception.quantityKg.toString())
            InfoRow(label = "Temperatura de Recepción:", value = reception.temperature.toString())
            InfoRow(label = "Estado:", value = reception.isCompleted.toString())
        }
    }

}


@Composable
fun WineBatchDetailCard(_batch: WineBatchResponse) {
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
                model = _batch.urlImage,
                contentDescription = "Wine Batch Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            InfoRow(label = "Creado por:", value = _batch.createdBy)
            InfoRow(label = "Código Interno:", value = _batch.internalCode)
            InfoRow(label = "Campaña de Cosecha:", value = _batch.harvestCampaign)
            InfoRow(label = "Viñedo de Origen:", value = _batch.vineyardOrigin)
            InfoRow(label = "Variedad de Uva:", value = _batch.grapeVariety)
            InfoRow(label = "Cantidad Inicial de Uva (kg):", value = _batch.initialGrapeQuantityKg.toString())
            InfoRow(label = "Estado:", value = _batch.status)
            InfoRow(label = "Etapa Actual:", value = _batch.currentStage)
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