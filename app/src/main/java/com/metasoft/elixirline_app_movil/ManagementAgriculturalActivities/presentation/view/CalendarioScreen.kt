package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioScreen(navController: NavController, viewModel: MainViewModel) {
    val darkRed = Color(0xFF8B0000)
    val fondo = Color(0xFFF2F8FF)

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val primerDiaMes = currentMonth.atDay(1)
    val primerDiaSemana = (primerDiaMes.dayOfWeek.value % 7)
    val totalDiasMes = currentMonth.lengthOfMonth()

    val diasMes = buildList<LocalDate?> {
        repeat(primerDiaSemana) { add(null) }
        for (dia in 1..totalDiasMes) {
            add(currentMonth.atDay(dia))
        }
    }

    val semanas = diasMes.chunked(7)

    val mesNombre = currentMonth.month
        .getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale(
                    "es",
                    "ES"
                )
            ) else it.toString()
        }

    val tasksPorFecha = viewModel.getTasksPorFecha()
    val tasks = viewModel.Tasks.collectAsState().value

    val tasksPorFechaReal = tasks.groupBy {
        Instant.parse(it.scheduledDate)
            .atZone(ZoneId.of("America/Lima"))
            .toLocalDate()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Calendario de Actividades", color = Color.White, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkRed)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFF8B0000))
            )
        },
        containerColor = fondo,
        floatingActionButton = {
            Button(
                onClick = { navController.navigate("nuevaTask") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                    brush = SolidColor(darkRed)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Agregar Nueva actividad", color = darkRed)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9)),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                    brush = SolidColor(darkRed)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            currentMonth = currentMonth.minusMonths(1)
                        }) {
                            Text("←", fontSize = 20.sp, color = Color.Black)
                        }
                        Text(
                            "$mesNombre ${currentMonth.year}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        IconButton(onClick = {
                            currentMonth = currentMonth.plusMonths(1)
                        }) {
                            Text("→", fontSize = 20.sp, color = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val diasSemana = listOf("DO", "LU", "MA", "MI", "JU", "VI", "SA")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        diasSemana.forEach { dia ->
                            Text(
                                dia,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                        }
                    }

                    semanas.forEach { semana ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.spacedBy((-12).dp)
                        ) {
                            semana.forEach { dia ->
                                Box(
                                    modifier = Modifier
                                        .size(48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (dia != null) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = dia.dayOfMonth.toString(),
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                            tasksPorFecha[dia]?.let { icono ->
                                                Text(
                                                    text = icono,
                                                    fontSize = 16.sp,
                                                    modifier = Modifier.clickable {
                                                        selectedDate = dia
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp)
            ) {
                Text("💧 Riego", color = Color.Black)
                Text("✂️ Poda", color = Color.Black)
                Text("🧪 Aplicación (fertilizante/pesticida)", color = Color.Black)
                Text("🍇 Cosecha", color = Color.Black)
                Text("🌱 Siembra o plantación nueva", color = Color.Black)
                Text("📌 2 o más Actividades", color = Color.Black)
            }
        }
        selectedDate?.let { date ->
            val actividadesDelDia = tasksPorFechaReal[date] ?: emptyList()
            AlertDialog(
                onDismissRequest = { selectedDate = null },
                confirmButton = {
                    TextButton(onClick = { selectedDate = null }) { Text("Cerrar") }
                },
                title = { Text("Detalles de ${date.dayOfMonth}/${date.monthValue}/${date.year}") },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 400.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (actividadesDelDia.isEmpty()) {
                            Text("No hay actividades para este día.")
                        } else {
                            actividadesDelDia.forEach { task ->
                                val parcel = viewModel.parcels.value.find { it.id == task.parcelId }

                                Text("Actividad: ${task.title}", fontWeight = FontWeight.Bold)
                                Text("Descripción: ${task.description}")
                                val zonedDateTime = Instant.parse(task.scheduledDate)
                                    .atZone(ZoneId.of("America/Lima"))

                                Text("Fecha: ${zonedDateTime.toLocalDate()}")
                                Text("Hora: ${zonedDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))}")
                                Text("Responsable: ${task.responsible}")

                                Spacer(modifier = Modifier.height(8.dp))

                                parcel?.let {
                                    Text("Lote: ${it.name}", fontWeight = FontWeight.Bold)
                                    Text("Variedad: ${it.cropType}")
                                    Text("Viñedo: ${it.location}")
                                    Text("Estado: ${it.status ?: "No especificado"}")
                                    Text("Etapa Actual: ${it.growthStage}")
                                    Text("Cantidad: ${it.yieldEstimate}")
                                } ?: Text("Información de lote no encontrada.")

                                Spacer(modifier = Modifier.height(12.dp))
                                Divider(color = Color.Gray, thickness = 1.dp)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            )
        }
    }
}