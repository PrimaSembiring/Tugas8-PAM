package com.example.profileapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileButtons(isDarkMode: Boolean) {

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

        Button(onClick = {}) {
            Text("Kirim Pesan")
        }

        OutlinedButton(
            onClick = {},
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isDarkMode) Color.Transparent else Color.LightGray,
                contentColor = if (isDarkMode) Color.White else Color.Black
            )
        ) {
            Text("Add Friend")
        }
    }
}