package com.example.profileapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class AppSettings(private val context: Context) {

    companion object {
        private val KEY_THEME = stringPreferencesKey("app_theme")
        private val KEY_SORT_ORDER = stringPreferencesKey("sort_order")
        private val KEY_NOTIFICATIONS = booleanPreferencesKey("notifications_enabled")
    }

    // Theme: "light", "dark", "system"
    val themeFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[KEY_THEME] ?: "system" }

    // Sort order: "newest", "oldest", "alphabetical"
    val sortOrderFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[KEY_SORT_ORDER] ?: "newest" }

    val notificationsEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[KEY_NOTIFICATIONS] ?: true }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_THEME] = theme
        }
    }

    suspend fun setSortOrder(sortOrder: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_SORT_ORDER] = sortOrder
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_NOTIFICATIONS] = enabled
        }
    }
}