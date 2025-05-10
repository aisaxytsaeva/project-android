package com.example.project_android

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*


class SharedViewModel : ViewModel() {
    private var _imageUri by mutableStateOf<Uri?>(null)
    val imageUri: Uri? get() = _imageUri

    fun setImageUri(uri: Uri) {
        _imageUri = uri
    }
}