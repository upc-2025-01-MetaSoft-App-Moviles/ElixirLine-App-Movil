package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.TaskRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Task
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModelFactory
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaTaskScreen(navController: NavController) {
    val darkRed = Color(0xFF8B0000)
    val backgroundColor = Color(0xFFF2F8FF)
    val context = LocalContext.current

    var tipoTask by remember { mutableStateOf("") }
    val opcionesTask = listOf("Riego", "Fertilización", "Cosecha", "Poda")

    val factory = remember { MainViewModelFactory() }
    val viewModel: MainViewModel = viewModel(factory = factory)

    val lotes by viewModel.parcels.collectAsStateWithLifecycle()

    var loteSeleccionado by remember { mutableStateOf<Parcel?>(null) }
    val opcionesLote = lotes

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
                    CampoSeleccion(
                        valorActual = loteSeleccionado?.name ?: "Seleccionar lote",
                        opciones = lotes.map { it.name },
                        onSeleccionar = { nombreSeleccionado ->
                            loteSeleccionado = lotes.find { it.name == nombreSeleccionado }
                        }
                    )

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
                    if (tipoTask.isBlank() || loteSeleccionado?.name.isNullOrBlank() || fecha.isBlank() || hora.isBlank() || responsable.isBlank()) {
                        Toast.makeText(context, "Por favor completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val parsedDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"))
                        .atTime(LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm")))
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toString()

                    println("Creando Task con parcelId = ${loteSeleccionado?.id} y name = ${loteSeleccionado?.name}")

                    if (loteSeleccionado != null) {
                        val nuevaTask = Task(
                            id = UUID.randomUUID().toString(),
                            title = tipoTask,
                            description = notas,
                            scheduledDate = parsedDate,
                            parcelId = loteSeleccionado!!.id,
                            status = 0
                        )
                        viewModel.addTask(nuevaTask) {
                            Toast.makeText(context, "Actividad guardada", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    } else {
                        Toast.makeText(context, "Selecciona un lote", Toast.LENGTH_SHORT).show()
                    }
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

