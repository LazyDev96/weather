package com.example.weather.feature.favorites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FavoritesDestination

/** @param onLocationSelected вызывается после выбора города, контейнер обычно переключает на Home. */
fun NavGraphBuilder.favoritesScreen(onLocationSelected: () -> Unit) {
    composable<FavoritesDestination> {
        FavoritesRoute(onLocationSelected = onLocationSelected)
    }
}
