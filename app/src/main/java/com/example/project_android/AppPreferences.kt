package com.example.project_android

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var isFirstLaunch: Boolean
        get() = sharedPreferences.getBoolean(FIRST_LAUNCH_KEY, true)
        set(value) = sharedPreferences.edit() { putBoolean(FIRST_LAUNCH_KEY, value) }

    companion object {
        private const val FIRST_LAUNCH_KEY = "first_launch"
    }
}