package com.example.project_android.camera

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import androidx.camera.core.ImageCaptureException
import android.util.Log
import com.example.project_android.R
import android.os.*


internal const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
internal const val PHOTO_EXTENSION = ".jpg"

internal fun Context.getOutputDirectory(): File {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        getMediaStoreDirectory()
    } else {
        getLegacyStorageDirectory()
    }
}

@Suppress("DEPRECATION")
private fun Context.getLegacyStorageDirectory(): File {
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
    } else {
        filesDir
    }
}

private fun Context.getMediaStoreDirectory(): File {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "Camera/${resources.getString(R.string.app_name)}"
        ).apply { mkdirs() }
    } else {
        getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: filesDir
    }
}

private fun Context.getScopedStorageDirectory(): File {
    return ContextCompat.getExternalFilesDirs(this, null).firstOrNull()?.let {
        File(it, "Camera").apply { mkdirs() }
    } ?: filesDir
}

internal suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.Companion.getInstance(this).also { future ->
            future.addListener({
                continuation.resume(future.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

internal fun takePhoto(
    imageCapture: ImageCapture,
    context: Context,
    onImageCaptured: (Uri) -> Unit
) {
    val outputDirectory = context.getOutputDirectory()
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis()) + PHOTO_EXTENSION
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    photoFile.absolutePath,
                    photoFile.name,
                    null
                )
                onImageCaptured(savedUri)
            }

            override fun onError(exc: ImageCaptureException) {
                Log.e("CameraCapture", "Photo capture failed: ${exc.message}", exc)
            }
        }
    )
}