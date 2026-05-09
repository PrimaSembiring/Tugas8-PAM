package com.example.profileapp.di

import com.example.profileapp.NotesDatabase
import com.example.profileapp.data.local.AppSettings
import com.example.profileapp.data.local.DatabaseDriverFactory
import com.example.profileapp.data.local.NoteLocalDataSource
import com.example.profileapp.data.remote.HttpClientFactory
import com.example.profileapp.data.remote.PostApiService
import com.example.profileapp.data.repository.NoteRepository
import com.example.profileapp.data.repository.PostRepository
import com.example.profileapp.platform.BatteryInfo
import com.example.profileapp.platform.DeviceInfo
import com.example.profileapp.platform.NetworkMonitor
import com.example.profileapp.viewmodel.NoteViewModel
import com.example.profileapp.viewmodel.PostViewModel
import com.example.profileapp.viewmodel.ProfileViewModel
import com.example.profileapp.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {


    /** DeviceInfo — informasi perangkat Android */
    single { DeviceInfo(androidContext()) }

    /** NetworkMonitor — monitor status koneksi internet */
    single { NetworkMonitor(androidContext()) }

    /** BatteryInfo — informasi baterai (bonus) */
    single { BatteryInfo(androidContext()) }

    /** DatabaseDriverFactory — SQLDelight Android driver */
    single { DatabaseDriverFactory(androidContext()) }

    /** NotesDatabase — singleton database instance */
    single { NotesDatabase(get<DatabaseDriverFactory>().createDriver()) }

    /** NoteLocalDataSource — abstraksi operasi database */
    single { NoteLocalDataSource(get()) }

    /** Ktor HTTP Client */
    single { HttpClientFactory.create() }

    /** PostApiService — API calls ke JSONPlaceholder */
    single { PostApiService(get()) }

    /** AppSettings — DataStore preferences */
    single { AppSettings(androidContext()) }


    /** NoteRepository — offline-first notes dengan sync API */
    single { NoteRepository(get(), get()) }

    /** PostRepository — repository untuk posts API */
    single { PostRepository(get()) }
    
    viewModel { NoteViewModel(get()) }
    viewModel { PostViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { SettingsViewModel(get()) }
}