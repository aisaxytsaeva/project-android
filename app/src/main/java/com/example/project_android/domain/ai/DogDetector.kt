package com.example.project_android.domain.ai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogDetector(private val context: Context) {

    private var interpreter: Interpreter? = null
    private val labels = mutableListOf<String>()

    companion object {
        private const val IMG_SIZE = 224
        private const val MODEL_NAME = "dog-breed-detector1.tflite"
        private const val MEAN = 128
        private const val STD = 128.0f
        private const val LABELS_FILE = "labels.txt"
        private const val TAG = "DogDetector"
    }

    init {
        try {
            initializeInterpreter()
            loadLabels()
            Log.d(TAG, "DogDetector initialized successfully")
        } catch (e: Exception) {
            throw DogDetectionException("Initialization failed", e)
        }
    }

    private fun initializeInterpreter() {
        val assetFileDescriptor = context.assets.openFd(MODEL_NAME)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val modelBuffer = fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.declaredLength
        )

        interpreter = Interpreter(modelBuffer)
        fileChannel.close()
        inputStream.close()
    }

    private fun loadLabels() {
        context.assets.open(LABELS_FILE).bufferedReader().useLines { lines ->
            labels.addAll(lines.map { it.trim() })
        }
        Log.d(TAG, "Loaded ${labels.size} labels")
    }

    suspend fun recognizeDog(imageUri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                // 1. Загрузка изображения
                val bitmap = context.loadBitmap(imageUri)
                    ?: throw IllegalArgumentException("Failed to decode image")

                // 2. Подготовка входных данных
                val inputBuffer = prepareImageInput(bitmap)

                // 3. Подготовка выходного буфера
                val output = Array(1) { FloatArray(labels.size) }

                // 4. Выполнение предсказания
                interpreter?.run(inputBuffer, output)
                    ?: throw IllegalStateException("Interpreter not initialized")

                // 5. Обработка результатов и возврат только названия породы
                processModelOutput(output[0])
            } catch (e: Exception) {
                Log.e(TAG, "Recognition error", e)
                null // Возвращаем null при ошибке
            }
        }
    }

    private fun prepareImageInput(bitmap: Bitmap): ByteBuffer {
        val inputBuffer = ByteBuffer.allocateDirect(4 * IMG_SIZE * IMG_SIZE * 3).apply {
            order(ByteOrder.nativeOrder())
        }

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMG_SIZE, IMG_SIZE, false)
        val pixels = IntArray(IMG_SIZE * IMG_SIZE)
        scaledBitmap.getPixels(pixels, 0, IMG_SIZE, 0, 0, IMG_SIZE, IMG_SIZE)

        for (pixel in pixels) {
            inputBuffer.putFloat(((pixel shr 16 and 0xFF) - MEAN) / STD) // R
            inputBuffer.putFloat(((pixel shr 8 and 0xFF) - MEAN) / STD)  // G
            inputBuffer.putFloat(((pixel and 0xFF) - MEAN) / STD)       // B
        }

        return inputBuffer
    }

    private fun processModelOutput(output: FloatArray): String {
        val maxIndex = output.indices.maxByOrNull { output[it] } ?: -1
        return if (maxIndex != -1 && maxIndex < labels.size) {
            labels[maxIndex]
        } else {
            throw IllegalStateException("Invalid model output")
        }
    }

    fun close() {
        interpreter?.close()
        interpreter = null
    }

    class DogDetectionException(message: String, cause: Throwable? = null) :
        Exception(message, cause)
}

fun Context.loadBitmap(uri: Uri): Bitmap? {
    return try {
        contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream)
        }
    } catch (e: Exception) {
        null
    }
}