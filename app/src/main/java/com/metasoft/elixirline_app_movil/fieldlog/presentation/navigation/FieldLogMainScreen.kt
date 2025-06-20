package com.metasoft.elixirline_app_movil.fieldlog.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.metasoft.elixirline_app_movil.fieldlog.presentation.view.FieldLogFormView
import com.metasoft.elixirline_app_movil.fieldlog.presentation.view.FieldLogHistoryView
import com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel.FieldLogViewModel
import com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel.FieldLogHistoryViewModel

@Composable
fun FieldLogMainScreen(
    historyViewModel: FieldLogHistoryViewModel,
    formViewModel: FieldLogViewModel
) {
    val navController = rememberNavController()

    Scaffold { padding ->
        NavHost(
            navController = navController,
            startDestination = "fieldLogHistory",
            modifier = Modifier.padding(padding)
        ) {
            composable("fieldLogHistory") {
                FieldLogHistoryView(viewModel = historyViewModel, navController = navController)
            }

            composable("fieldLogForm") {
                FieldLogFormView(viewModel = formViewModel, navController = navController)
            }
        }
    }
}
