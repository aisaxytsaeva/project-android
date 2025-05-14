package com.example.project_android.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.project_android.data.Prompt

@Dao
interface PromptDao {

    @Upsert // no conflict if two same id's
    fun addPrompt(prompt: Prompt)

    @Query("SELECT * FROM prompt")
    fun getAllPrompts(): LiveData<List<Prompt>>
}