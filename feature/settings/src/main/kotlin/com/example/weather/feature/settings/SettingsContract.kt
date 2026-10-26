package com.example.weather.feature.settings

import com.example.weather.core.common.mvi.UiEffect
import com.example.weather.core.common.mvi.UiEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.model.AppSettings

data class SettingsState(
    val settings: AppSettings = AppSettings(),
) : UiState

sealed interface SettingsEvent : UiEvent {
    data object LanguageClicked : SettingsEvent
    data object ThemeClicked : SettingsEvent
    data class WeatherNotificationToggled(val enabled: Boolean) : SettingsEvent
}

sealed interface SettingsEffect : UiEffect {
    data object OpenLanguage : SettingsEffect
    data object OpenTheme : SettingsEffect
}
