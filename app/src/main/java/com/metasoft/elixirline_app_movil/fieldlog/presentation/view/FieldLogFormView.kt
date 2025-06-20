package com.metasoft.elixirline_app_movil.fieldlog.presentation.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel.FieldLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldLogFormView(viewModel: FieldLogViewModel, navController: NavController) {
    var description by remember { mutableStateOf("") }
    val entryTypes = listOf("Observation", "Incident", "CompletedTask")
    var expanded by remember { mutableStateOf(false) }
    var selectedEntryType by remember { mutableStateOf("") }
    val selectedImages = remember { mutableStateListOf<Uri>() }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        selectedImages.clear()
        selectedImages.addAll(uris)
    }

    val loading by viewModel.loading.collectAsState()
    val success by viewModel.success.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val genericUuid = "3fa85f64-5717-4562-b3fc-2c963f66afa6"

    LaunchedEffect(Unit) {
        viewModel.resetState()
        description = ""
        selectedEntryType = ""
        selectedImages.clear()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (!success) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedEntryType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Entrada") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    modifier = Modifier
                        .menuAnchor() // quitamos el parámetro `enabled`
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    entryTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedEntryType = type
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Seleccionar Imagen")
            }

            selectedImages.forEach { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Button(
                onClick = {
                    viewModel.submitEntryWithImages(
                        authorId = genericUuid,
                        parcelId = genericUuid,
                        description = description,
                        entryType = selectedEntryType,
                        relatedTaskId = genericUuid,
                        images = selectedImages.toList()
                    )
                },
                enabled = !loading && selectedEntryType.isNotBlank(),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Enviar")
            }

            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            errorMessage?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }
        } else {
            Text("¡Entrada enviada exitosamente!", color = MaterialTheme.colorScheme.primary)

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Volver al historial")
            }
        }
    }
}
