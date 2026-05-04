package com.example.profileapp.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.example.profileapp.R
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ProfileSection(
    name: String,
    bio: String,
    isDarkMode: Boolean,
    onEditClick: () -> Unit
) {
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subText = if (isDarkMode) Color(0xFFB0BEC5) else Color.DarkGray

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .border(3.dp, Color.White, CircleShape)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkMode)
                    Color.White.copy(alpha = 0.08f)
                else
                    Color(0xFFF5F5F5)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(name, color = textColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Teknik Informatika", color = subText)
                Text(bio, color = Color(0xFF00E5FF), fontWeight = FontWeight.SemiBold)
                Text("Institut Teknologi Sumatera (ITERA)", color = subText)

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = onEditClick) {
                    Text("Edit Profile")
                }
            }
        }
    }
}