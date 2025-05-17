package com.metasoft.elixirline_app_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.metasoft.elixirline_app_movil.presentation.navigation.AppNavHost
import com.metasoft.elixirline_app_movil.presentation.view.worker.WorkerListScreen
import com.metasoft.elixirline_app_movil.presentation.viewmodel.worker.WorkerViewModel
import com.metasoft.elixirline_app_movil.ui.theme.ElixirLineAppMovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElixirLineAppMovilTheme {
                AppNavHost()
            }
        }
    }
}
