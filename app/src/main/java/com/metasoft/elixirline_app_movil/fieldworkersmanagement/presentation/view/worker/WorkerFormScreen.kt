package com.metasoft.elixirline_app_movil.fieldworkersmanagement.presentation.view.worker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metasoft.elixirline_app_movil.fieldworkersmanagement.data.local.worker.WorkerEntity
import java.text.SimpleDateFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun WorkerFormScreen(
    onSave: (WorkerEntity) -> Unit,
    onCancel: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var activo by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Trabajador") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF880000),
                    titleContentColor = Color.White
                )

            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre completo") })
            OutlinedTextField(value = dni, onValueChange = { dni = it }, label = { Text("DNI") })
            OutlinedTextField(value = celular, onValueChange = { celular = it }, label = { Text("Celular") })
            OutlinedTextField(value = rol, onValueChange = { rol = it }, label = { Text("Rol") })

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = activo, onCheckedChange = { activo = it })
                Text("Activo actualmente")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val trabajador = WorkerEntity(
                        nombre = nombre,
                        dni = dni,
                        celular = celular,
                        rol = rol,
                        fechaInicio = fecha,
                        activo = activo
                    )
                    onSave(trabajador)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8800d4))
            ) {
                Text("Guardar", color = Color.White)
            }
        }
    }
}
