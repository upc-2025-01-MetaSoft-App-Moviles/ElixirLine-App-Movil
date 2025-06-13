package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view.*

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("nuevaTask") { NuevaTaskScreen(navController) }
        composable("calendario") { CalendarioScreen(navController) }
        composable("misLotes") { MisLotesScreen(navController) }
        composable("nuevoLote") { NuevoLoteScreen(navController) }
    }
}
