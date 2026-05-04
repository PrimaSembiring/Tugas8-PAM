package com.example.profileapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.profileapp.viewmodel.ProfileViewModel
import com.example.profileapp.ui.components.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onEditClick: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (state.isDarkMode) {
                    Modifier.background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF0F2027),
                                Color(0xFF203A43),
                                Color(0xFF2C5364)
                            )
                        )
                    )
                } else {
                    Modifier.background(Color.White)
                }
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Switch(
                checked = state.isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(60.dp))

            ProfileSection(
                name = state.name,
                bio = state.bio,
                isDarkMode = state.isDarkMode,
                onEditClick = onEditClick
            )

            Spacer(Modifier.height(15.dp))

            ProfileButtons(isDarkMode = state.isDarkMode)

            Spacer(Modifier.height(30.dp))

            ContactCard(isDarkMode = state.isDarkMode)

            Spacer(Modifier.height(20.dp))

            SkillSection(isDarkMode = state.isDarkMode)
        }
    }
}