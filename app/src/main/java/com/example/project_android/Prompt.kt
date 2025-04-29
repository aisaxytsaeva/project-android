package com.example.project_android

import androidx.room.*

@Entity(tableName = "prompt")
data class Prompt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,//maybe add image save
    val description: String,
)
