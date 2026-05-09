package com.example.profileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.profileapp.navigation.Screen
import com.example.profileapp.ui.screen.*
import com.example.profileapp.viewmodel.*
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Semua ViewModel di-inject via Koin
            val profileVM: ProfileViewModel = koinViewModel()
            val noteVM: NoteViewModel = koinViewModel()
            val postVM: PostViewModel = koinViewModel()
            val settingsVM: SettingsViewModel = koinViewModel()

            val navController = rememberNavController()

            Scaffold(
                bottomBar = {
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route

                    NavigationBar {
                        NavigationBarItem(
                            selected = currentRoute == Screen.Notes.route,
                            onClick = {
                                navController.navigate(Screen.Notes.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Default.List, contentDescription = null) },
                            label = { Text("Notes") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.Favorites.route,
                            onClick = {
                                navController.navigate(Screen.Favorites.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                            label = { Text("Favorites") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.Profile.route,
                            onClick = {
                                navController.navigate(Screen.Profile.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Default.Person, contentDescription = null) },
                            label = { Text("Profile") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.Settings.route,
                            onClick = {
                                navController.navigate(Screen.Settings.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                            label = { Text("Settings") }
                        )
                    }
                }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Notes.route,
                    modifier = Modifier.padding(padding)
                ) {
                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            viewModel = profileVM,
                            onEditClick = { navController.navigate(Screen.EditProfile.route) }
                        )
                    }
                    composable(Screen.EditProfile.route) {
                        EditProfileScreen(
                            viewModel = profileVM,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(Screen.Notes.route) {
                        NotesScreen(
                            viewModel = noteVM,
                            postViewModel = postVM,
                            onClickDetail = {
                                navController.navigate(Screen.NoteDetail.createRoute(it))
                            },
                            onAdd = { navController.navigate(Screen.AddNote.route) }
                        )
                    }
                    composable(Screen.NoteDetail.route) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("noteId")?.toInt() ?: 0
                        NoteDetailScreen(
                            noteId = id,
                            viewModel = noteVM,
                            postViewModel = postVM,
                            onBack = { navController.popBackStack() },
                            onEdit = { navController.navigate(Screen.EditNote.createRoute(it)) }
                        )
                    }
                    composable(Screen.AddNote.route) {
                        AddNoteScreen(
                            viewModel = noteVM,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(Screen.EditNote.route) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("noteId")?.toInt() ?: 0
                        EditNoteScreen(
                            noteId = id,
                            viewModel = noteVM,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(Screen.Favorites.route) {
                        FavoritesScreen(viewModel = noteVM)
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(viewModel = settingsVM)
                    }
                }
            }
        }
    }
}