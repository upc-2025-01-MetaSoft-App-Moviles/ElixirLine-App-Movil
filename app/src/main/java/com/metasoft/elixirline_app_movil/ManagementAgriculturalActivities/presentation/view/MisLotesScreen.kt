package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisLotesScreen(navController: NavController) {
    val darkRed = Color(0xFF8B0000)

    val factory = remember { MainViewModelFactory() }
    val viewModel: MainViewModel = viewModel(factory = factory)

    val lotes by viewModel.parcels.collectAsStateWithLifecycle()
    val shouldRefresh = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("shouldRefresh", false)
        ?.collectAsState()

    LaunchedEffect(shouldRefresh?.value) {
        println("shouldRefresh.value = ${shouldRefresh?.value}")
        if (shouldRefresh?.value == true) {
            viewModel.loadParcels()
            navController.currentBackStackEntry?.savedStateHandle?.set("shouldRefresh", false)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Mis Lotes", color = Color.White, fontSize = 20.sp)
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
                    .background(darkRed)
            )
        },
        floatingActionButton = {
            Button(
                onClick = { navController.navigate("nuevoLote") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF2F8FF)),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                    brush = SolidColor(darkRed)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Agregar Lote", color = Color.Black)
            }
        },
        containerColor = Color(0xFFF2F8FF)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (lotes.isNotEmpty()) {
                lotes.forEachIndexed { index, lote ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9)),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 2.dp,
                            brush = SolidColor(Color(0xFF8B0000))
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Lote ${index + 1} - ${lote.name}",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Variedad: ")
                                    }
                                    append(lote.cropType)
                                },
                                color = Color.Black
                            )
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Etapa: ")
                                    }
                                    append(lote.growthStage)
                                },
                                color = Color.Black
                            )
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Últ. actividad: ")
                                    }
                                    append(lote.lastTask)
                                },
                                color = Color.Black
                            )
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Rendimiento estimado: ")
                                    }
                                    append(lote.yieldEstimate)
                                },
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}