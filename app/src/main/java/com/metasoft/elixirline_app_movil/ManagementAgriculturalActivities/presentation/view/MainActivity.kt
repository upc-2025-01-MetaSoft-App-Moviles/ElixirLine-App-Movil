package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.navigation.NavigationGraph
import com.metasoft.elixirline_app_movil.ui.theme.ElixirLineAppMovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElixirLineAppMovilTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}
