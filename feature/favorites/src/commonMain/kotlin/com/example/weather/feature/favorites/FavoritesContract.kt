package com.example.weather.feature.favorites

import com.example.weather.core.common.mvi.UiEffect
import com.example.weather.core.common.mvi.UiEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.model.WeatherLocation

data class FavoritesState(
    val items: List<WeatherLocation> = emptyList(),
    val isLoading: Boolean = true,
) : UiState

sealed interface FavoritesEvent : UiEvent {
    data class LocationClicked(val location: WeatherLocation) : FavoritesEvent
    data class RemoveClicked(val locationId: String) : FavoritesEvent
}

sealed interface FavoritesEffect : UiEffect {
    /** Город из избранного стал выбранным, можно показывать его погоду. */
    data object LocationSelected : FavoritesEffect
}
