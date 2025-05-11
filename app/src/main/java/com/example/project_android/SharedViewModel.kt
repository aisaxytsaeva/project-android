package com.example.project_android

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: Uri? get() = _imageUri.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: String? get() = _errorMessage.value

    private val _breedName = mutableStateOf<String?>(null)
    val breedName: String? get() = _breedName.value

    private val dogDetector by lazy {
        try {
            DogDetector(application.applicationContext).also {
                Log.d("SharedVM", "Dog detector initialized successfully")
            }
        } catch (e: Exception) {
            Log.e("SharedVM", "Detector init failed", e)
            _errorMessage.value = "Failed to load model"
            null
        }
    }

    fun setImageUri(uri: Uri) {
        if (!isValidUri(uri)) {
            _errorMessage.value = "Invalid image URI"
            return
        }

        _imageUri.value = uri
        resetAnalysisState()
        Log.d("SharedVM", "Image URI set: $uri")
    }

    fun analyzeImage() {
        val currentUri = _imageUri.value ?: run {
            _errorMessage.value = "No image selected"
            return
        }

        if (dogDetector == null) {
            _errorMessage.value = "Model not available"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null
        _breedName.value = null

        viewModelScope.launch {
            try {
                Log.d("SharedVM", "Starting analysis for URI: $currentUri")

                // Проверка доступности изображения
                val inputStream = try {
                    getApplication<Application>().contentResolver.openInputStream(currentUri)
                } catch (e: IOException) {
                    null
                }

                if (inputStream == null) {
                    _errorMessage.value = "Failed to open image"
                    return@launch
                }
                inputStream.close()

                // Выполнение детекции
                val breed = dogDetector?.recognizeDog(currentUri)

                // Обновление результатов
                breed?.let {
                    _breedName.value = it
                    Log.d("SharedVM", "Analysis successful. Breed: $it")
                } ?: run {
                    _errorMessage.value = "No dog detected"
                }

            } catch (e: Exception) {
                Log.e("SharedVM", "Analysis failed", e)
                _errorMessage.value = when (e) {
                    is IOException -> "Image processing error"
                    else -> "Detection failed: ${e.localizedMessage}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun isValidUri(uri: Uri): Boolean {
        return try {
            val cr: ContentResolver = getApplication<Application>().contentResolver
            cr.openInputStream(uri)?.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun resetAnalysisState() {
        _isLoading.value = false
        _errorMessage.value = null
        _breedName.value = null
    }

    fun clearAll() {
        _imageUri.value = null
        resetAnalysisState()
    }
}