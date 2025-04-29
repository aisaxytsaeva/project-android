package com.example.project_android

import androidx.lifecycle.*
import kotlinx.coroutines.*

class PromptViewModel : ViewModel() {

    val promptDao = MainApplication.promptDatabase.getPromptDao()

    val searchList : LiveData<List<Prompt>> = promptDao.getAllPrompts()

    fun addSearch(name: String, imagePath: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            promptDao.addPrompt(prompt = Prompt(name = name, description = description))
        }
    }
}