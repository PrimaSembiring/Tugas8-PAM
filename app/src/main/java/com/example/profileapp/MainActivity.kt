package com.example.profileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

import com.example.profileapp.data.local.AppSettings
import com.example.profileapp.data.local.DatabaseDriverFactory
import com.example.profileapp.data.local.NoteLocalDataSource
import com.example.profileapp.data.remote.HttpClientFactory
import com.example.profileapp.data.remote.PostApiService
import com.example.profileapp.data.repository.NoteRepository
import com.example.profileapp.data.repository.PostRepository
import com.example.profileapp.NotesDatabase

import com.example.profileapp.viewmodel.*
import com.example.profileapp.ui.screen.*
import com.example.profileapp.navigation.Screen

class MainActivity : ComponentActivity() {

    // 🔥 Dependencies (manual DI)
    private val appSettings: AppSettings by lazy {
        AppSettings(applicationContext)
    }

    private val driverFactory: DatabaseDriverFactory by lazy {
        DatabaseDriverFactory(applicationContext)
    }

    private val database: NotesDatabase by lazy {
        NotesDatabase(driverFactory.createDriver())
    }

    private val localDataSource: NoteLocalDataSource by lazy {
        NoteLocalDataSource(database)
    }

    private val httpClient by lazy {
        HttpClientFactory.create()
    }

    private val postApiService: PostApiService by lazy {
        PostApiService(httpClient)
    }

    // 🔥 FIX PALING STABIL (pakai type explicit)
    private val noteRepository: NoteRepository by lazy {
        NoteRepository(localDataSource, postApiService)
    }

    private val postRepository: PostRepository by lazy {
        PostRepository(postApiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val profileVM: ProfileViewModel = viewModel()

            val noteVM: NoteViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return NoteViewModel(noteRepository) as T
                    }
                }
            )

            val postVM: PostViewModel = viewModel()

            val settingsVM: SettingsViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return SettingsViewModel(appSettings) as T
                    }
                }
            )

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
                            onEditClick = {
                                navController.navigate(Screen.EditProfile.route)
                            }
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
                            onAdd = {
                                navController.navigate(Screen.AddNote.route)
                            }
                        )
                    }

                    composable(Screen.NoteDetail.route) { backStackEntry ->
                        val id =
                            backStackEntry.arguments?.getString("noteId")?.toInt() ?: 0

                        NoteDetailScreen(
                            noteId = id,
                            viewModel = noteVM,
                            postViewModel = postVM,
                            onBack = { navController.popBackStack() },
                            onEdit = {
                                navController.navigate(Screen.EditNote.createRoute(it))
                            }
                        )
                    }

                    composable(Screen.AddNote.route) {
                        AddNoteScreen(
                            viewModel = noteVM,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.EditNote.route) { backStackEntry ->
                        val id =
                            backStackEntry.arguments?.getString("noteId")?.toInt() ?: 0

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