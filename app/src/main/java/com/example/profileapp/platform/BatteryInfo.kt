package com.example.profileapp.platform

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

/**
 * BatteryInfo - Platform-specific implementation untuk Android (BONUS)
 * Mengambil informasi baterai menggunakan BatteryManager API
 */
class BatteryInfo(private val context: Context) {

    private val batteryManager =
        context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    /** Level baterai dalam persen (0-100) */
    fun getBatteryLevel(): Int {
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    /** Cek apakah perangkat sedang diisi daya */
    fun isCharging(): Boolean {
        val intent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
    }

    /** Status baterai sebagai teks */
    fun getBatteryStatus(): String {
        val level = getBatteryLevel()
        val charging = isCharging()
        return when {
            charging && level >= 100 -> "Penuh ⚡"
            charging -> "Mengisi Daya ⚡ ($level%)"
            level >= 80 -> "Baik 🟢 ($level%)"
            level >= 50 -> "Sedang 🟡 ($level%)"
            level >= 20 -> "Rendah 🟠 ($level%)"
            else -> "Kritis 🔴 ($level%)"
        }
    }
}