package com.example.project_android

import kotlinx.serialization.Serializable
import android.net.Uri

sealed interface Route {
    @Serializable
    data object GreetingsScreen: Route

    @Serializable
    data object SelectionScreen: Route

    @Serializable
    data class ImagePreviewScreen(
        val imageUri: String? = null
    ) : Route {
        companion object {
            val base = ImagePreviewScreen()

            fun withArgs(uri: Uri? = null): ImagePreviewScreen {
                return ImagePreviewScreen(imageUri = uri?.toString())
            }
        }

        fun toRouteString(): String {
            return if (imageUri != null) {
                "preview?imageUri=$imageUri"
            } else {
                "preview"
            }
        }
    }

    @Serializable
    data object ResultsScreen: Route


}