package com.example.project_android

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object GreetingsScreen: Route

    @Serializable
    data object ResultsScreen: Route

    @Serializable
    data object HistoryScreen: Route
}