package com.example.profileapp.platform

import android.content.Context
import android.os.Build

/**
 * DeviceInfo - Platform-specific implementation untuk Android
 * Mengambil informasi perangkat menggunakan Android Build API
 */
class DeviceInfo(private val context: Context) {

    /** Nama model perangkat, contoh: "Samsung Galaxy S21" */
    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
        val model = Build.MODEL
        return if (model.startsWith(manufacturer, ignoreCase = true)) {
            model
        } else {
            "$manufacturer $model"
        }
    }

    /** Versi OS Android, contoh: "Android 14 (API 34)" */
    fun getOsVersion(): String {
        return "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    }

    fun getAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "1.0"
        } catch (e: Exception) {
            "1.0"
        }
    }

    fun getDeviceBrand(): String {
        return Build.BRAND.replaceFirstChar { it.uppercase() }
    }

    /** Board/chipset perangkat */
    fun getBoard(): String {
        return Build.BOARD
    }
}