package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view.*
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaTaskScreen(navController: NavController) {
    val darkRed = Color(0xFF8B0000)
    val backgroundColor = Color(0xFFF2F8FF)
    val context = LocalContext.current

    var tipoTask by remember { mutableStateOf("") }
    val opcionesTask = listOf("Riego", "Fertilización", "Cosecha", "Poda")

    var loteSeleccionado by remember { mutableStateOf("") }
    val opcionesLote = listOf("Lote A", "Lote B", "Lote C")

    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var responsable by remember { mutableStateOf("") }
    val opcionesResponsables = listOf("Juan Pérez", "Ana Gómez", "Carlos Ruiz")

    var notas by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Nueva Actividad", color = Color.White, fontSize = 20.sp)
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
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9)),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp, brush = SolidColor(Color(0xFF8B0000))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    TituloCampo("Tipo de actividad:")
                    CampoSeleccion(valorActual = tipoTask, opciones = opcionesTask) {
                        tipoTask = it
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9)),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp, brush = SolidColor(Color(0xFF8B0000))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    TituloCampo("Seleccionar lote:")
                    CampoSeleccion(valorActual = loteSeleccionado, opciones = opcionesLote) {
                        loteSeleccionado = it
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TituloCampo("Fecha:")
                    var showDatePicker by remember { mutableStateOf(false) }

                    if (showDatePicker) {
                        val calendar = Calendar.getInstance()
                        android.app.DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                fecha = "$dayOfMonth/${month + 1}/$year"
                                showDatePicker = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).apply {
                            setOnCancelListener { showDatePicker = false }
                        }.show()
                    }

                    Button(
                        onClick = { showDatePicker = true },
                        colors = ButtonDefaults.buttonColors(containerColor = darkRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (fecha.isNotEmpty()) fecha else "Seleccionar fecha",
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    TituloCampo("Hora:")
                    CampoTexto(valor = hora, onValorChange = { hora = it }, placeholder = "HH:mm")

                    Spacer(modifier = Modifier.height(12.dp))

                    TituloCampo("Responsable:")
                    CampoSeleccion(valorActual = responsable, opciones = opcionesResponsables) {
                        responsable = it
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TituloCampo("Notas (Opcional):")
                    CampoTexto(valor = notas, onValorChange = { notas = it }, placeholder = "Escribir...")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(containerColor = darkRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar actividad", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

