package com.metasoft.elixirline_app_movil.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.metasoft.elixirline_app_movil.presentation.view.worker.WorkerFormScreen
import com.metasoft.elixirline_app_movil.presentation.view.worker.WorkerListScreen
import com.metasoft.elixirline_app_movil.presentation.viewmodel.worker.WorkerViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val workerViewModel: WorkerViewModel = viewModel()

    NavHost(navController = navController, startDestination = "worker_list") {
        composable("worker_list") {
            WorkerListScreen(
                workers = workerViewModel.allWorkers,
                onAddClick = { navController.navigate("worker_form") }
            )
        }
        composable("worker_form") {
            WorkerFormScreen(
                onSave = { worker ->
                    workerViewModel.insert(worker)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
