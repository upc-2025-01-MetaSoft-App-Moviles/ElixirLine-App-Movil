package com.metasoft.elixirline_app_movil.supplies.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.metasoft.elixirline_app_movil.supplies.presentation.view.SupplyDetailView
import com.metasoft.elixirline_app_movil.supplies.presentation.view.SupplyHistoryView
import com.metasoft.elixirline_app_movil.supplies.presentation.view.SupplyListView
import com.metasoft.elixirline_app_movil.supplies.presentation.view.SupplyRegistrationView
import com.metasoft.elixirline_app_movil.supplies.presentation.view.SupplyUsageView
import java.util.UUID

@Composable
fun SupplyNavigation() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    val navigationItems = listOf(
        NavigationItem(Icons.Default.Home, "Inicio", "supply_list"),
        NavigationItem(Icons.Default.Add, "Registrar", "supply_registration"),
        NavigationItem(Icons.Default.ShoppingCart, "Uso", "supply_usage"),
        NavigationItem(Icons.Default.Search, "Historial", "supply_history")
    )

    androidx.compose.material3.Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF8B0000)
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selectedItem == index) Color.White else Color.LightGray
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                color = if (selectedItem == index) Color.White else Color.LightGray
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        SupplyNavHost(navController, paddingValues)
    }
}

@Composable
fun SupplyNavHost(navController: NavHostController, paddingValues: androidx.compose.foundation.layout.PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "supply_list",
        modifier = androidx.compose.ui.Modifier.padding(paddingValues)
    ) {
        composable("supply_list") {
            SupplyListView(
                onAddClick = { navController.navigate("supply_registration") },
                onItemClick = { supplyId ->
                    navController.navigate("supply_detail/$supplyId")
                },
                onRegisterUsageClick = { supplyId ->
                    navController.navigate("supply_usage/$supplyId")
                }
            )
        }

        composable("supply_registration") {
            SupplyRegistrationView(
                onSuccess = { navController.navigate("supply_list") },
                onCancel = { navController.navigateUp() }
            )
        }

        composable("supply_detail/{supplyId}") { backStackEntry ->
            val supplyId = backStackEntry.arguments?.getString("supplyId")
            if (supplyId != null) {
                SupplyDetailView(
                    supplyId = UUID.fromString(supplyId),
                    onBack = { navController.navigateUp() },
                    onSuccess = { navController.navigate("supply_list") }
                )
            }
        }

        composable("supply_usage") {
            SupplyUsageView(
                onSuccess = { navController.navigate("supply_list") },
                onCancel = { navController.navigateUp() }
            )
        }

        composable("supply_usage/{supplyId}") { backStackEntry ->
            val supplyId = backStackEntry.arguments?.getString("supplyId")
            if (supplyId != null) {
                SupplyUsageView(
                    preselectedSupplyId = UUID.fromString(supplyId),
                    onSuccess = { navController.navigate("supply_list") },
                    onCancel = { navController.navigateUp() }
                )
            }
        }

        composable("supply_history") {
            SupplyHistoryView(
                onBack = { navController.navigateUp() }
            )
        }
    }
}

data class NavigationItem(
    val icon: ImageVector,
    val title: String,
    val route: String
)
