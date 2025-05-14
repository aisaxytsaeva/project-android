package com.example.project_android

import androidx.lifecycle.*
import kotlinx.coroutines.*

class PromptViewModel : ViewModel() {

    val promptDao = MainApplication.promptDatabase.getPromptDao()

    val promptList : LiveData<List<Prompt>> = promptDao.getAllPrompts()

    fun addPrompt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            promptDao.addPrompt(prompt = Prompt(name = name))
        }
    }
}