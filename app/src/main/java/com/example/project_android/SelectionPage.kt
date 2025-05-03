package com.example.project_android


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
    onImageSelected: (Uri) -> Unit,
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
    var selectedImageUri by remember { mutableStateOf<Uri?>(null)}

    SelectionPage(
        onCameraSelected = {},
        onImageSelected = {uri-> selectedImageUri = uri},
        onImageUriCaptured = {uri -> capturedImageUri = uri},

        )

}





