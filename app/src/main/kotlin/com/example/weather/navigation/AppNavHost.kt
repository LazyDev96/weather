package com.example.weather.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.weather.feature.main.MainDestination
import com.example.weather.feature.main.mainScreen
import com.example.weather.feature.splash.SplashDestination
import com.example.weather.feature.splash.splashScreen

private const val FADE_DURATION_MS = 500

/**
 * Корневой граф: Splash -> Main.
 * Переход выполняется кросс-фейдом, а Splash удаляется из back stack,
 * чтобы кнопка «Назад» с главного экрана закрывала приложение.
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination,
        enterTransition = { fadeIn(tween(FADE_DURATION_MS)) },
        exitTransition = { fadeOut(tween(FADE_DURATION_MS)) },
        popEnterTransition = { fadeIn(tween(FADE_DURATION_MS)) },
        popExitTransition = { fadeOut(tween(FADE_DURATION_MS)) },
    ) {
        splashScreen(
            onFinished = {
                navController.navigate(MainDestination) {
                    popUpTo(SplashDestination) { inclusive = true }
                }
            },
        )
        mainScreen()
    }
}
