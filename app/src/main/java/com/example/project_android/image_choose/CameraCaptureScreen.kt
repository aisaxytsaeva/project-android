package com.example.project_android.image_choose

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import androidx.camera.core.*
import com.example.project_android.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
//camera ui
fun CameraCaptureScreen(
    onImageCaptured: (Uri) -> Unit,
    onBackPressed: () -> Unit

) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    LaunchedEffect(cameraPermissionState.status) {
        if (cameraPermissionState.status.isGranted) {
            startCamera(
                context = context,
                lifecycleOwner = lifecycleOwner,
                onCameraInitialized = { capture -> imageCapture = capture }
            )
        } else {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            imageCapture = imageCapture
        )



        IconButton(
            onClick = {
                imageCapture?.let { capture ->
                    takePhoto(
                        imageCapture = capture,
                        context = context,
                        onImageCaptured = onImageCaptured
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Take photo",
                modifier = Modifier.size(64.dp),
                tint = Color.White
            )
        }
    }
}


