package com.example.profileapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) }
    )
}