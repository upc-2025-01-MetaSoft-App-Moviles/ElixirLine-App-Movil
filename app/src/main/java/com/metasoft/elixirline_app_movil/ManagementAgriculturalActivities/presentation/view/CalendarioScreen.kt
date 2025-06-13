package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioScreen(navController: NavController) {
    val darkRed = Color(0xFF8B0000)
    val fondo = Color(0xFFF2F8FF)

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val primerDiaMes = currentMonth.atDay(1)
    val diasEnMes = currentMonth.lengthOfMonth()
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
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale("es", "ES")) else it.toString() }

    val TasksPorFecha = mapOf(
        currentMonth.atDay(3) to "💧",
        currentMonth.atDay(7) to "✂️",
        currentMonth.atDay(12) to "🧪",
        currentMonth.atDay(18) to "🍇",
        currentMonth.atDay(25) to "🌱"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Calendario de Taskes", color = Color.White, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
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
                Text("Agregar Nueva Task", color = darkRed)
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
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp, brush = SolidColor(darkRed))
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
                                            Text(
                                                text = TasksPorFecha[dia] ?: "",
                                                fontSize = 16.sp
                                            )
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
            }
        }
    }
}