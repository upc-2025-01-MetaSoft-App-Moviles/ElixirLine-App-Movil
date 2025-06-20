package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.navigation.NavigationGraph
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModel
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.viewmodel.MainViewModelFactory
import com.metasoft.elixirline_app_movil.ui.theme.ElixirLineAppMovilTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = MainViewModelFactory()
        val viewModel: MainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setContent {
            ElixirLineAppMovilTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavigationGraph(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
