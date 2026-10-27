package com.example.weather.feature.explore

import com.example.weather.core.common.mvi.UiEffect
import com.example.weather.core.common.mvi.UiEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.model.WeatherLocation

enum class SearchStatus { IDLE, LOADING, SUCCESS, ERROR }

data class ExploreState(
    val query: String = "",
    val results: List<WeatherLocation> = emptyList(),
    val status: SearchStatus = SearchStatus.IDLE,
    /** Город, который сейчас используется на вкладке Home. */
    val selectedId: String? = null,
) : UiState

sealed interface ExploreEvent : UiEvent {
    data class QueryChanged(val query: String) : ExploreEvent
    data class ResultClicked(val location: WeatherLocation) : ExploreEvent
    data object RetryClicked : ExploreEvent
}

sealed interface ExploreEffect : UiEffect {
    /** Город выбран и сохранён: можно вернуться на Home. */
    data object NavigateToHome : ExploreEffect
}
