package com.example.profileapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SkillSection(isDarkMode: Boolean) {

    val titleColor = if (isDarkMode) Color.White else Color.Black
    val contentColor = if (isDarkMode) Color(0xFFB0BEC5) else Color.DarkGray

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode)
                Color.White.copy(alpha = 0.08f)
            else Color(0xFFF5F5F5)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text("Skills", color = titleColor)

            SkillCategory("Programming","Python, SQL, R", contentColor)
            SkillCategory("Data Analysis","Pandas, NumPy, EDA, Data Cleaning", contentColor)
            SkillCategory("Machine Learning","Classification, Regression, Clustering", contentColor)

            Text(
                "Random Forest, XGBoost, LightGBM",
                color = contentColor,
                modifier = Modifier.padding(start = 10.dp)
            )

            SkillCategory("Visualization","Matplotlib, Seaborn, Tableau", contentColor)
            SkillCategory("Statistics","Hypothesis Testing, Probability, Regression", contentColor)
        }
    }
}

@Composable
fun SkillCategory(title: String, content: String, color: Color) {
    Column {
        Text(title, color = Color(0xFF90CAF9))
        Text(content, color = color)
    }
}