package com.example.project_android

import androidx.room.*

@Database(
    entities = [Prompt::class],
    version = 1
)
abstract class PromptDatabase : RoomDatabase() {
    companion object {
        const val NAME = "Search_DB"
    }

    abstract fun getPromptDao() : PromptDao
}