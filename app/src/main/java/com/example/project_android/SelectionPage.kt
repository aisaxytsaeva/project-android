package com.example.project_android

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.project_android.image_choose.CameraCaptureScreen
import com.example.project_android.image_choose.rememberGalleryAccess


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionPage(
    onCameraSelected: () -> Unit,
    onImageSelected: (Bitmap) -> Unit,
    onImageUriCaptured: (Uri) -> Unit,
    onBackPressed: () -> Unit
) {
    var showCamera by remember { mutableStateOf(false) }
    var showPreview by remember { mutableStateOf(false) }
    var showSelection by remember { mutableStateOf(true) }
    var previewImageUri by remember { mutableStateOf<Uri?>(null) }
    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    val galleryAccess = rememberGalleryAccess { bitmap ->
        previewBitmap = bitmap
        showPreview = true
    }

    if (showPreview) {
        ImagePreviewScreen(
            imageUri = previewImageUri,
            bitmap = previewBitmap,
            onConfirm = {
                previewImageUri?.let(onImageUriCaptured)
                previewBitmap?.let(onImageSelected)
                showPreview = false
            },
            onReturnToSelectionPage = {
                showPreview = false
                showSelection = true
            },

        )
    } else if (showCamera) {
        CameraCaptureScreen(
            onImageCaptured = { uri ->
                previewImageUri = uri
                showPreview = true
                showCamera = false
            },
            onBackPressed = {
                showCamera = false
                onBackPressed()
            }
        )
    } else {
        ImageSourceScreen(
            onCameraSelected = { showCamera = true },
            onGallerySelected = { galleryAccess.selectImage() },
            onBackPressed = onBackPressed
        )
    }
}

@Preview
@Composable
fun ImageSelectionFlow(){
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null)}

    SelectionPage(
        onCameraSelected = {},
        onImageSelected = {bitmap -> selectedImageBitmap = bitmap},
        onImageUriCaptured = {uri -> capturedImageUri = uri},
        onBackPressed = {}

    )

}





