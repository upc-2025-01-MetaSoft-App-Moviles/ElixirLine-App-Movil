package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.os.Build
import android.text.format.DateUtils.formatDateTime
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModelFactory
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController) {
    val factory = remember { MainViewModelFactory() }
    val viewModel: MainViewModel = viewModel(factory = factory)

    val weather by viewModel.weatherInfo.collectAsStateWithLifecycle()
    val Tasks by viewModel.Tasks.collectAsStateWithLifecycle()

    val darkRed = Color(0xFF8B0000)
    val fondo = Color(0xFFF2F8FF)

    Scaffold(
        containerColor = fondo,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(darkRed)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(fondo)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkRed)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Taskes Agrícolas",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoCard(title = "Clima Actual") {
                weather?.let {
                    Text("${it.temperatura} – ${it.descripcion}", color = Color.Black)
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Humedad: ")
                            }
                            append(it.humedad)
                        },
                        color = Color.Black
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Viento: ")
                            }
                            append(it.viento)
                        },
                        color = Color.Black
                    )
                } ?: Text("Cargando clima...", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoCard(title = "Próximas Tasks agendadas") {
                if (Tasks.isNotEmpty()) {
                    Tasks.forEach {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("${formatDateTime(it.scheduledDate)}: ")
                                }
                                append(it.description)
                            },
                            color = Color.Black
                        )
                    }
                } else {
                    Text("No hay Tasks agendadas.", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Accesos rápidos:",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickAccessButton(
                        text = "Nueva Task",
                        modifier = Modifier.weight(1f).defaultMinSize(minWidth = 140.dp)
                    ) { navController.navigate("nuevaTask") }

                    QuickAccessButton(
                        text = "Calendario",
                        modifier = Modifier.weight(1f)
                    ) { navController.navigate("calendario") }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    QuickAccessButton(
                        text = "Mis Lotes",
                        modifier = Modifier.width(160.dp)
                    ) { navController.navigate("misLotes") }
                }
            }

            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun InfoCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp, brush = SolidColor(Color(0xFF8B0000))),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun QuickAccessButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF2F8FF)),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp, brush = SolidColor(Color(0xFF8B0000))),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(vertical = 6.dp)
    ) {
        Text(text = text, color = Color.Black, fontSize = 14.sp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(isoDateTime: String): String {
    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val dateTime = OffsetDateTime.parse(isoDateTime, formatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy – HH:mm")
        outputFormatter.format(dateTime)
    } catch (e: Exception) {
        "Fecha inválida"
    }
}