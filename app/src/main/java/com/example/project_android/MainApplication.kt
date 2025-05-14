package com.example.project_android

import android.app.Application
import androidx.room.Room
import com.example.project_android.data.PromptDatabase


class MainApplication : Application() {
    companion object {
        lateinit var promptDatabase: PromptDatabase
    }

    override fun onCreate() {
        super.onCreate()
        promptDatabase = Room.databaseBuilder(
            applicationContext,
            PromptDatabase::class.java,
            PromptDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}