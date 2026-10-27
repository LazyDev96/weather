package com.example.weather.feature.explore

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ExploreDestination

/** @param onCitySelected вызывается после выбора города, контейнер обычно переключает на Home. */
fun NavGraphBuilder.exploreScreen(onCitySelected: () -> Unit) {
    composable<ExploreDestination> {
        ExploreRoute(onCitySelected = onCitySelected)
    }
}
