package com.example.project_android.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Prompt::class],
    version = 2
)
abstract class PromptDatabase : RoomDatabase() {
    companion object {
        const val NAME = "Search_DB"
    }

    abstract fun getPromptDao() : PromptDao
}