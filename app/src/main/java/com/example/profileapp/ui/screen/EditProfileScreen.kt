package com.example.profileapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.profileapp.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.profileapp.ui.components.MyTextField

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf(state.name) }
    var bio by remember { mutableStateOf(state.bio) }

    Column(modifier = Modifier.padding(16.dp)) {

        MyTextField(
            value = name,
            onValueChange = { name = it },
            label = "Name"
        )

        Spacer(modifier = Modifier.height(8.dp))

        MyTextField(
            value = bio,
            onValueChange = { bio = it },
            label = "Bio"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.updateProfile(name, bio)
            onBack()
        }) {
            Text("Save")
        }
    }
}