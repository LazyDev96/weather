package com.example.weather.feature.home

import com.example.weather.core.common.mvi.UiEffect
import com.example.weather.core.common.mvi.UiEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.model.Weather

/**
 * Что показывать:
 * - [weather] != null — данные (из кэша или свежие), поверх них при [isRefreshing] идёт индикатор;
 * - [weather] == null и [hasError] — экран ошибки с кнопкой повтора;
 * - иначе — первичная загрузка.
 */
data class HomeState(
    val weather: Weather? = null,
    val isRefreshing: Boolean = false,
    val hasError: Boolean = false,
    /** Выбранный город есть в избранном. */
    val isFavorite: Boolean = false,
) : UiState

sealed interface HomeEvent : UiEvent {
    data object RefreshClicked : HomeEvent
    data object FavoriteClicked : HomeEvent
}

sealed interface HomeEffect : UiEffect {
    /** Ручное обновление не удалось; на экране остаются данные из кэша. */
    data object RefreshFailed : HomeEffect
}
