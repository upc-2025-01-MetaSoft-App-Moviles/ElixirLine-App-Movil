package com.metasoft.elixirline_app_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.metasoft.elixirline_app_movil.ui.theme.ElixirLineAppMovilTheme
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.navigation.Home

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElixirLineAppMovilTheme {
                Home()
            }
        }
    }
}
