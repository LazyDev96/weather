package com.example.weather.feature.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination

fun NavGraphBuilder.splashScreen(onFinished: () -> Unit) {
    composable<SplashDestination> {
        SplashRoute(onFinished = onFinished)
    }
}
