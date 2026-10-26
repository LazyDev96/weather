package com.example.weather.feature.settings

import com.example.weather.core.common.mvi.UiEffect
import com.example.weather.core.common.mvi.UiEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.model.ThemeMode

data class ThemeState(
    val selected: ThemeMode = ThemeMode.Default,
) : UiState

sealed interface ThemeEvent : UiEvent {
    data class ThemeSelected(val theme: ThemeMode) : ThemeEvent
    data object BackClicked : ThemeEvent
}

sealed interface ThemeEffect : UiEffect {
    data object NavigateBack : ThemeEffect
}
