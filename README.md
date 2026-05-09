# ProfileApp - Platform-Specific Features

## 👤 Identitas Mahasiswa

NAMA : PRIMA AGUSTA SEMBIRING
NIM  : 123140119

---

## Deskripsi Aplikasi

Aplikasi **ProfileApp** adalah upgrade dari tugas Minggu 7 yang menambahkan fitur **Platform-Specific Features** menggunakan **Koin Dependency Injection**, **DeviceInfo**, **NetworkMonitor**, dan **BatteryInfo**. Semua dependencies dikelola melalui Koin sehingga arsitektur lebih bersih, modular, dan mudah di-test.

---

##  Fitur yang Diimplementasikan

### 1. 🔧 Koin Dependency Injection
- Setup Koin di `ProfileApp.kt` sebagai Application class
- `AppModule.kt` mendaftarkan semua dependencies:
  - Database, Repository, ViewModel
  - Platform services (DeviceInfo, NetworkMonitor, BatteryInfo)
  - Networking (Ktor), Settings (DataStore)
- Semua ViewModel di-inject via `koinViewModel()`
- Platform services di-inject via `koinInject()` di Composable

### 2. DeviceInfo — Platform-Specific 
- Implementasi menggunakan Android `Build` API
- Informasi yang ditampilkan:
  - Nama & Brand perangkat
  - Versi OS Android + API level
  - Versi aplikasi
  - Board/chipset

### 3. NetworkMonitor — Platform-Specific
- Implementasi menggunakan Android `ConnectivityManager`
- Memonitor status koneksi secara **realtime** via `Flow<Boolean>`
- Network Status Indicator muncul otomatis di `NotesScreen` saat offline
- Menggunakan `AnimatedVisibility` untuk animasi masuk/keluar

### 4. UI Integration 
- **NotesScreen**: Network indicator merah di bagian atas saat offline
- **SettingsScreen**: Section Device Info dan Battery Info yang di-inject via Koin

### 5. Architecture 
- Clean separation: `platform/`, `di/`, `data/`, `viewmodel/`, `ui/`
- Koin module terpisah per layer
- Tidak ada tight coupling antar komponen

### 6. Code Quality 
- Komentar dokumentasi di setiap class
- Penamaan yang konsisten dan deskriptif
- Single Responsibility Principle diterapkan

### 7. BatteryInfo — Bonus 
- Implementasi menggunakan Android `BatteryManager` API
- Informasi yang ditampilkan:
  - Level baterai dalam persen
  - Status charging/tidak charging
  - Kondisi baterai (Baik/Sedang/Rendah/Kritis)

---

## Arsitektur Proyek

```
profileapp/
├── di/
│   └── AppModule.kt              → Koin DI module (semua dependencies)
├── platform/
│   ├── DeviceInfo.kt             → Info perangkat Android (Build API)
│   ├── NetworkMonitor.kt         → Monitor koneksi (ConnectivityManager)
│   └── BatteryInfo.kt            → Info baterai (BatteryManager) 
├── data/
│   ├── local/
│   │   ├── AppSettings.kt        → DataStore Preferences
│   │   ├── DatabaseDriverFactory.kt → SQLDelight Driver
│   │   └── NoteLocalDataSource.kt   → Database operations
│   ├── remote/
│   │   ├── HttpClientFactory.kt  → Ktor HTTP Client
│   │   ├── PostApiService.kt     → API calls
│   │   └── PostDto.kt            → Data model API
│   └── repository/
│       ├── NoteRepository.kt     → Offline-first notes
│       └── PostRepository.kt     → Posts API
├── navigation/
│   └── Screen.kt                 → Route navigasi
├── ui/
│   ├── components/               → Reusable components
│   └── screen/
│       ├── NotesScreen.kt        → Notes + Network Indicator 
│       ├── SettingsScreen.kt     → Settings + Device Info 
│       ├── AddNoteScreen.kt
│       ├── EditNoteScreen.kt
│       ├── FavoritesScreen.kt
│       ├── ProfileScreen.kt
│       └── NoteDetailScreen.kt
├── viewmodel/
│   ├── NoteViewModel.kt
│   ├── PostViewModel.kt
│   ├── ProfileViewModel.kt
│   └── SettingsViewModel.kt
├── ProfileApp.kt                 → Application class, init Koin 
└── MainActivity.kt               → Entry point, koinViewModel() 
```
---
## Arsitektur Dependency Injection
```
ProfileApp (Application)
↓ startKoin()
AppModule
├── Platform Layer
│   ├── DeviceInfo(context)
│   ├── NetworkMonitor(context)
│   └── BatteryInfo(context)
├── Database Layer
│   ├── DatabaseDriverFactory(context)
│   ├── NotesDatabase(driver)
│   └── NoteLocalDataSource(db)
├── Network Layer
│   ├── HttpClient
│   └── PostApiService(client)
├── Settings Layer
│   └── AppSettings(context)
├── Repository Layer
│   ├── NoteRepository(local, api)
│   └── PostRepository(api)
└── ViewModel Layer
├── NoteViewModel(repo)
├── PostViewModel()
├── ProfileViewModel()
└── SettingsViewModel(settings)
```
---
## 📱 Screenshots

### Notes Screen (Network Indicator saat Offline)
![Notes Offline](screenshots/notes_offline.png)

### Settings Screen (Device Info + Battery Info)
![Settings Device Info](screenshots/settings_device_info.png)

### Settings Screen (Battery Info)
![Settings Battery](screenshots/settings_battery.png)

---
