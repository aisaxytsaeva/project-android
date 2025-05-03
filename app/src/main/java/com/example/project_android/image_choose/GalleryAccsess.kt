package com.example.project_android.image_choose


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext



@Composable
fun rememberGalleryAccess(
    onImageSelected: (Uri) -> Unit
): GalleryAccess {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { safeUri ->

            try {
                context.contentResolver.openInputStream(safeUri)?.use {
                    onImageSelected(safeUri)
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(context, "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show()
            }
        }
    }

    return GalleryAccess(
        selectImage = { launcher.launch("image/*") }
    )
}

class GalleryAccess(
    val selectImage: () -> Unit
)