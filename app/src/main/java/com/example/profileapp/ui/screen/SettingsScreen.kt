package com.example.profileapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.profileapp.platform.BatteryInfo
import com.example.profileapp.platform.DeviceInfo
import com.example.profileapp.viewmodel.SettingsViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {

    val currentTheme by viewModel.currentTheme.collectAsState()
    val currentSortOrder by viewModel.currentSortOrder.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

    // ✅ DeviceInfo & BatteryInfo di-inject via Koin
    val deviceInfo: DeviceInfo = koinInject()
    val batteryInfo: BatteryInfo = koinInject()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "Pengaturan",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // ── DEVICE INFO ───────────────────────────────────────
        SettingsSection(title = "📱 Informasi Perangkat") {
            DeviceInfoRow(label = "Perangkat", value = deviceInfo.getDeviceName())
            DeviceInfoRow(label = "Brand", value = deviceInfo.getDeviceBrand())
            DeviceInfoRow(label = "Sistem Operasi", value = deviceInfo.getOsVersion())
            DeviceInfoRow(label = "Versi Aplikasi", value = "v${deviceInfo.getAppVersion()}")
            DeviceInfoRow(label = "Board", value = deviceInfo.getBoard())
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ── BATTERY INFO (BONUS) ──────────────────────────────
        SettingsSection(title = "🔋 Status Baterai") {
            DeviceInfoRow(label = "Level Baterai", value = "${batteryInfo.getBatteryLevel()}%")
            DeviceInfoRow(
                label = "Status",
                value = if (batteryInfo.isCharging()) "Mengisi Daya ⚡" else "Tidak Mengisi Daya"
            )
            DeviceInfoRow(label = "Kondisi", value = batteryInfo.getBatteryStatus())
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ── TEMA ──────────────────────────────────────────────
        SettingsSection(title = "🎨 Tampilan") {
            Text(
                text = "Tema Aplikasi",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThemeChip(
                    label = "Light",
                    icon = Icons.Default.Star,
                    selected = currentTheme == "light",
                    onClick = { viewModel.setTheme("light") },
                    modifier = Modifier.weight(1f)
                )
                ThemeChip(
                    label = "Dark",
                    icon = Icons.Default.Favorite,
                    selected = currentTheme == "dark",
                    onClick = { viewModel.setTheme("dark") },
                    modifier = Modifier.weight(1f)
                )
                ThemeChip(
                    label = "System",
                    icon = Icons.Default.Home,
                    selected = currentTheme == "system",
                    onClick = { viewModel.setTheme("system") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ── URUTAN TAMPILAN ───────────────────────────────────
        SettingsSection(title = "📋 Urutan Notes") {
            listOf(
                "newest" to "Terbaru",
                "oldest" to "Terlama",
                "alphabetical" to "Alfabetis"
            ).forEach { (value, label) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentSortOrder == value,
                        onClick = { viewModel.setSortOrder(value) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(label)
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ── NOTIFIKASI ───────────────────────────────────────
        SettingsSection(title = "🔔 Notifikasi") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Aktifkan Notifikasi",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Terima pengingat untuk notes penting",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { viewModel.setNotificationsEnabled(it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ── INFO STATUS ───────────────────────────────────────
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Column {
                    Text(
                        text = "Pengaturan tersimpan otomatis",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Tema: ${currentTheme.replaceFirstChar { it.uppercase() }} • Urutan: $currentSortOrder",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DeviceInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            modifier = Modifier.weight(1.5f)
        )
    }
    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
private fun ThemeChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        },
        modifier = modifier
    )
}