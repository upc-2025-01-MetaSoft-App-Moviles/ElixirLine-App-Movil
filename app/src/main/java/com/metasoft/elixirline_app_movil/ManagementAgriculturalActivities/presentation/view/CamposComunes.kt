package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TituloCampo(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CampoTexto(valor: String, onValorChange: (String) -> Unit, placeholder: String = "") {
    BasicTextField(
        value = valor,
        onValueChange = onValorChange,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8B0000), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        decorationBox = { innerTextField ->
            if (valor.isEmpty()) {
                Text(placeholder, color = Color.LightGray)
            }
            innerTextField()
        },
        textStyle = LocalTextStyle.current.copy(color = Color.White)
    )
}

@Composable
fun CampoSeleccion(valorActual: String, opciones: List<String>, onSeleccionar: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8B0000), shape = RoundedCornerShape(8.dp))
            .clickable { expanded = true }
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = if (valorActual.isEmpty()) "Seleccionar..." else valorActual,
            color = Color.White
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccionar(opcion)
                        expanded = false
                    }
                )
            }
        }
    }
}
