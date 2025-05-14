package com.example.project_android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_android.MainApplication
import com.example.project_android.data.Prompt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PromptViewModel : ViewModel() {

    val promptDao = MainApplication.Companion.promptDatabase.getPromptDao()

    val promptList : LiveData<List<Prompt>> = promptDao.getAllPrompts()

    fun addPrompt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            promptDao.addPrompt(prompt = Prompt(name = name))
        }
    }
}