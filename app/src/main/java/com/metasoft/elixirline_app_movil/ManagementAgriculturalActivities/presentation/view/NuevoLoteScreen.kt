package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.model.Parcel
import java.util.*
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModelFactory
import androidx.compose.runtime.livedata.observeAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoLoteScreen(navController: NavHostController, viewModel: MainViewModel) {
    val darkRed = Color(0xFF8B0000)
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val factory = remember { MainViewModelFactory() }
    val viewModel: MainViewModel = viewModel(factory = factory)

    var nombreLote by remember { mutableStateOf("") }
    var variedad by remember { mutableStateOf("") }
    var viñedo by remember { mutableStateOf("") }
    var fechaRecepcion by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var etapa by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    val variedades = listOf("Cabernet", "Merlot")
    val estados = listOf("Saludable", "Enfermo")
    val etapas = listOf("Siembra", "Crecimiento", "Cosecha")

    val shouldRefresh = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Boolean>("shouldRefresh")?.observeAsState()

    LaunchedEffect(shouldRefresh?.value) {
        if (shouldRefresh?.value == true) {
            viewModel.loadParcels()
            navController.currentBackStackEntry?.savedStateHandle?.set("shouldRefresh", false)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Nuevo Lote", color = Color.White, fontSize = 20.sp)
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
                    .background(darkRed)
            )
        },
        containerColor = Color(0xFFF2F8FF)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TituloCampo("Nombre del lote:")
                    CampoTexto(valor = nombreLote) { nombreLote = it }

                    TituloCampo("Variedad:")
                    CampoSeleccion(variedad, variedades) { variedad = it }

                    TituloCampo("Viñedo:")
                    CampoTexto(valor = viñedo) { viñedo = it }

                    TituloCampo("Fecha de recepción:")
                    var showDatePicker by remember { mutableStateOf(false) }

                    if (showDatePicker) {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                fechaRecepcion = "$dayOfMonth/${month + 1}/$year"
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
                            text = if (fechaRecepcion.isNotEmpty()) fechaRecepcion else "Seleccionar fecha",
                            color = Color.White
                        )
                    }

                    TituloCampo("Estado:")
                    CampoSeleccion(status, estados) { status = it }

                    TituloCampo("Etapa actual:")
                    CampoSeleccion(etapa, etapas) { etapa = it }

                    TituloCampo("Cantidad (hectáreas):")
                    CampoTexto(valor = cantidad) { cantidad = it }

                    TituloCampo("Notas (Opcional):")
                    CampoTexto(valor = notas) { notas = it }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nombreLote.isBlank() ||
                        variedad.isBlank() ||
                        viñedo.isBlank() ||
                        fechaRecepcion.isBlank() ||
                        status.isBlank() ||
                        etapa.isBlank() ||
                        cantidad.isBlank()
                    ) {
                        Toast.makeText(
                            context,
                            "Por favor completa todos los campos antes de guardar.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val nuevoLote = Parcel(
                        id = UUID.randomUUID().toString(),
                        name = nombreLote,
                        cropType = variedad,
                        location = viñedo,
                        growthStage = etapa,
                        lastTask = "",
                        yieldEstimate = cantidad,
                        status = status
                    )

                    viewModel.addParcel(nuevoLote) {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("shouldRefresh", true)

                        Toast.makeText(
                            context,
                            "Lote guardado",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = darkRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Guardar Lote", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun CampoTexto(valor: String, onValueChange: (String) -> Unit) {
    TextField(
        value = valor,
        onValueChange = onValueChange,
        placeholder = { Text("Escribir...", color = Color.White) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF8B0000),
            unfocusedContainerColor = Color(0xFF8B0000),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
}