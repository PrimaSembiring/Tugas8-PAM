package com.example.profileapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp



@Composable
fun ContactCard(isDarkMode: Boolean) {

    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color(0xFFB0BEC5) else Color.Gray

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode)
                Color.White.copy(alpha = 0.08f)
            else
                Color(0xFFF5F5F5)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ContactItem(Icons.Default.Email, "Email", "prima.123140119@itera.ac.id", textColor, subTextColor)
            ContactItem(Icons.Default.Phone, "WhatsApp", "+62 81262697797", textColor, subTextColor)
            ContactItem(Icons.Default.LocationOn, "Location", "Lampung, Indonesia", textColor, subTextColor)
        }
    }
}

@Composable
fun ContactItem(
    icon: ImageVector,
    title: String,
    value: String,
    titleColor: Color,
    valueColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color(0xFF00E5FF),
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(title, color = titleColor, fontWeight = FontWeight.Bold)
            Text(value, color = valueColor)
        }
    }
}