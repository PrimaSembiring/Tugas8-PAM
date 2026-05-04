package com.example.profileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profileapp.data.local.AppSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val appSettings: AppSettings
) : ViewModel() {

    val currentTheme: StateFlow<String> = appSettings.themeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "system")

    val currentSortOrder: StateFlow<String> = appSettings.sortOrderFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "newest")

    val notificationsEnabled: StateFlow<Boolean> = appSettings.notificationsEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setTheme(theme: String) {
        viewModelScope.launch {
            appSettings.setTheme(theme)
        }
    }

    fun setSortOrder(sortOrder: String) {
        viewModelScope.launch {
            appSettings.setSortOrder(sortOrder)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appSettings.setNotificationsEnabled(enabled)
        }
    }
}