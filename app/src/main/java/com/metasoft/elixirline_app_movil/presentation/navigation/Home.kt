package com.metasoft.elixirline_app_movil.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.metasoft.elixirline_app_movil.presentation.view.FindAllProductionHistoryView

@Preview
@Composable
fun Home() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        NavigationItem(icon = Icons.Default.Info, "Production History", "production_history"),
    )

    val selectedIndex = remember {
        mutableStateOf(0)
    }

    Scaffold (
        bottomBar = {
            BottomAppBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedIndex.value,
                        onClick = {
                            selectedIndex.value = index
                            navController.navigate(item.route)
                        },
                        label = {
                            Text(item.title)
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = "production_history", modifier = Modifier.padding(padding)) {
            composable("production_history") { FindAllProductionHistoryView() }
        }
    }
}


data class NavigationItem(
    val icon: ImageVector,
    val title: String,
    val route: String,
)
