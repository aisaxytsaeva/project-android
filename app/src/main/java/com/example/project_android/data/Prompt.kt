package com.example.project_android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prompt")
data class Prompt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,//maybe add image save
)