package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response.WineBatchResponse
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
        NavigationItem(Icons.Default.Refresh, "Prceso de Vinificación", "WineBatches"),
        NavigationItem(Icons.Default.Create, "Gestion de Insumos", "Insumos"),
        NavigationItem(Icons.Default.Place, "Actividades Agricolas", "Agricolas"),
        NavigationItem(Icons.Default.AccountBox, "Gestion de Empleados", "Empleados"),
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
                        // Lógica para cerrar sesión o redirigir
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
                    NavigationBarItem (
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
                        onClick = {
                            //Al seleccionar un lote, se guarda el lote seleccionado
                            wineBatch ->
                            selectedWineBatch.value = wineBatch
                            navController.navigate("WineBatchDetail")
                        },
                        onEditClick = { wineBatch ->
                            selectedWineBatch.value = wineBatch
                            navController.navigate("WineBatchesEdit/${wineBatch.id}")
                        }
                    )
                }
                composable("WineBatchDetail"){
                    WineBatchDetailView(
                        onBack = {
                            navController.navigate("WineBatches") {
                                popUpTo("WineBatches") { inclusive = true }
                            }
                        },
                        onAddStageClick = {
                            navController.navigate("WineBatchStageAdd/${it.arguments?.getString("wineBatchId") ?: ""}")
                            // Pasar el ID del lote seleccionado al agregar una nueva etapa
                        },
                        batchId = selectedWineBatch.value?.id ?: "",
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