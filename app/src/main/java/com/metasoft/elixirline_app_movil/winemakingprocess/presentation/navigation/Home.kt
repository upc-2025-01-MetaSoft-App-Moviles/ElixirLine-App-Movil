package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.navigation

import com.metasoft.elixirline_app_movil.ProductionHistory.presentation.view.FindAllProductionHistoryView
import com.metasoft.elixirline_app_movil.fieldworkersmanagement.presentation.navigation.AppNavHost
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view.WineBatchDetailView
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view.WineBatchesListView

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.*

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.metasoft.elixirline_app_movil.fieldlog.presentation.di.PresentationModule
import com.metasoft.elixirline_app_movil.fieldlog.presentation.navigation.FieldLogMainScreen
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view.WineBatchDetailView
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view.WineBatchesListView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    val navController = rememberNavController()
    val selectedWineBatch = remember { mutableStateOf<WineBatchResponse?>(null) }
    val selectedIndex = remember { mutableStateOf(0) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navigationItems = listOf(
        NavigationItem(Icons.Default.Refresh, "Proceso de Vinificación", "WineBatches"),
        NavigationItem(Icons.Default.Create, "Gestión de Insumos", "Insumos"),
        NavigationItem(Icons.Default.Place, "Actividades Agrícolas", "Agricolas"),
        NavigationItem(Icons.Default.AccountBox, "Gestión de Empleados", "Empleados"),
        NavigationItem(Icons.Default.Info, "Historial de Producción", "ProductionHistory"),
        NavigationItem(Icons.Default.Info, "Bitácora", "FieldLogHistory"),
    )


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Elixir Line",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                navigationItems.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(text = item.title)
                            }
                        },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route) {
                                popUpTo(item.route) { inclusive = true }
                            }
                        }
                    )
                }

                NavigationDrawerItem(
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar sesión", modifier = Modifier.padding(end = 8.dp))
                            Text("Cerrar sesión")
                        }
                    },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("Login") {
                            popUpTo(0) // Limpia el backstack
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFF8B0000),
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            selectedIndex.value = 1
                            navController.navigate("Perfil")
                        },
                        icon = {
                            Icon(Icons.Default.AccountBox, contentDescription = "Perfil", tint = Color.White)
                        },
                        label = {
                            Text("Perfil", color = Color.White)
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            selectedIndex.value = 0
                            scope.launch { drawerState.open() }
                        },
                        icon = {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        },
                        label = {
                            Text("Menu", color = Color.White)
                        }
                    )
                }
            },
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "WineBatches",
                modifier = Modifier.padding(padding)
            ) {
                composable("WineBatches") {
                    WineBatchesListView(
                        onBack = {
                            navController.navigate("WineBatches") {
                                popUpTo("WineBatches") { inclusive = true }
                            }
                        },
                        onAdd = {
                            navController.navigate("WineBatchesAdd")
                        },
                        onClick = { wineBatch ->
                            selectedWineBatch.value = wineBatch
                            navController.navigate("WineBatchDetail")
                        },
                        onEditClick = { wineBatch ->
                            selectedWineBatch.value = wineBatch
                            navController.navigate("WineBatchesEdit/${wineBatch.id}")
                        }
                    )
                }

                composable("WineBatchDetail") {
                    WineBatchDetailView(
                        onBack = {
                            navController.navigate("WineBatches") {
                                popUpTo("WineBatches") { inclusive = true }
                            }
                        },
                        onAddStageClick = {
                            navController.navigate("WineBatchStageAdd/${it.arguments?.getString("wineBatchId") ?: ""}")
                        },
                        batchId = remember { selectedWineBatch.value?.id ?: "" },
                    )
                }

                composable("ProductionHistory") {
                    FindAllProductionHistoryView()
                }


                composable("Empleados") {
                    AppNavHost()
                }

                composable("FieldLogHistory") {
                    FieldLogMainScreen(
                        historyViewModel = PresentationModule.getFieldLogHistoryViewModel(),
                        formViewModel = PresentationModule.getFieldLogViewModel(LocalContext.current)
                    )
                }

            }
        }
    }
}

data class NavigationItem(
    val icon: ImageVector,
    val title: String,
    val route: String
)
