package com.example.weather.feature.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MainDestination

fun NavGraphBuilder.mainScreen() {
    composable<MainDestination> {
        MainScreen()
    }
}
