package com.example.project_android

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface PromptDao {

    @Upsert // no conflict if two same id's
    fun addPrompt(prompt: Prompt)

    @Query("SELECT * FROM prompt")
    fun getAllPrompts(): LiveData<List<Prompt>>
}