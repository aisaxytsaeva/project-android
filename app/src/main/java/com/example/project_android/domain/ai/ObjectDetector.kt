package com.example.project_android.domain.ai

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.*
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.io.FileInputStream

class EfficientDetDetector(context: Context) {
    private val interpreter: Interpreter
    private val appContext = context.applicationContext

    init {
        val assetFileDescriptor = try {
            appContext.assets.openFd("1.tflite").also {
                check(it.length > 0) { "Model file is empty" }
            }
        } catch (e: IOException) {
            throw IllegalStateException("Failed to load model file", e)
        }

        interpreter = try {
            Interpreter(
                assetFileDescriptor.mapReadOnly(),
                Interpreter.Options().apply {
                }
            )
        } catch (e: Exception) {
            throw IllegalStateException("Failed to initialize interpreter", e)
        } finally {
            assetFileDescriptor.close()
        }
    }

    fun detect(imageUri: Uri): List<DetectionResult> {
        return try {
            val bitmap = loadBitmapFromUri(imageUri) ?:
            throw IllegalArgumentException("Failed to decode image from URI")

            val inputBuffer = prepareInput(bitmap)
            bitmap.recycle()

            val outputs = prepareOutputBuffers()

            runModel(inputBuffer, outputs)


            processOutputs(outputs)
        } catch (e: Exception) {
            throw DetectionException("Detection failed", e)
        }
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            appContext.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun prepareInput(bitmap: Bitmap): ByteBuffer {
        val inputImage = Bitmap.createScaledBitmap(bitmap, 320, 320, true)
        val buffer = ByteBuffer.allocateDirect(320 * 320 * 3 * 4).apply {
            order(ByteOrder.nativeOrder())
        }

        for (y in 0 until 320) {
            for (x in 0 until 320) {
                val pixel = inputImage.getPixel(x, y)
                buffer.putFloat(Color.red(pixel) / 255f)
                buffer.putFloat(Color.green(pixel) / 255f)
                buffer.putFloat(Color.blue(pixel) / 255f)
            }
        }
        inputImage.recycle()
        return buffer
    }

    private fun prepareOutputBuffers(): OutputBuffers {
        return OutputBuffers(
            locations = Array(1) { FloatArray(20 * 4) },
            classes = Array(1) { FloatArray(20) },
            scores = Array(1) { FloatArray(20) },
            numDetections = FloatArray(1)
        )
    }

    private fun runModel(input: ByteBuffer, outputs: OutputBuffers) {
        interpreter.runForMultipleInputsOutputs(
            arrayOf(input),
            mapOf(
                0 to outputs.locations,
                1 to outputs.classes,
                2 to outputs.scores,
                3 to outputs.numDetections
            )
        )
    }

    private fun processOutputs(outputs: OutputBuffers): List<DetectionResult> {
        val results = mutableListOf<DetectionResult>()
        val actualDetections = outputs.numDetections[0].toInt().coerceAtMost(20)

        for (i in 0 until actualDetections) {
            if (outputs.scores[0][i] > 0.5f) {
                results.add(
                    DetectionResult(
                        label = "Class ${outputs.classes[0][i].toInt()}",
                        confidence = outputs.scores[0][i],
                        boundingBox = parseBoundingBox(outputs.locations[0], i)
                    )
                )
            }
        }
        return results
    }

    private fun parseBoundingBox(locations: FloatArray, index: Int): RectF {
        val startPos = index * 4
        return RectF(
            locations[startPos + 1], // y1
            locations[startPos],     // x1
            locations[startPos + 3], // y2
            locations[startPos + 2]  // x2
        )
    }

    fun close() {
        interpreter.close()
    }

    // Data classes
    data class OutputBuffers(
        val locations: Array<FloatArray>,
        val classes: Array<FloatArray>,
        val scores: Array<FloatArray>,
        val numDetections: FloatArray
    )

    data class DetectionResult(
        val label: String,
        val confidence: Float,
        val boundingBox: RectF
    )

    class DetectionException(message: String, cause: Throwable? = null) :
        Exception(message, cause)


    private fun AssetFileDescriptor.mapReadOnly(): ByteBuffer {
        return FileInputStream(fileDescriptor).channel.map(
            FileChannel.MapMode.READ_ONLY,
            startOffset,
            declaredLength
        ).also {
            close()
        }
    }
}