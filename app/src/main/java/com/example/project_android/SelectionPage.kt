package com.example.project_android

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.project_android.camera.CameraCaptureScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionPage(
    onCameraSelected: () -> Unit,
    onImageSelected: (Bitmap) -> Unit,
    onImageUriCaptured: (Uri) -> Unit,


) {
    var showCamera by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val galleryAccess = rememberGalleryAccess(onImageSelected)

    if (showCamera) {
        CameraCaptureScreen(
            onImageCaptured = { uri ->
                onImageUriCaptured(uri)
                showCamera = false
            },

        )
    }else {
        ImageSourceScreen(
            onCameraSelected = { showCamera = true },
            onGallerySelected = { galleryAccess.selectImage() },

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

    )

}





