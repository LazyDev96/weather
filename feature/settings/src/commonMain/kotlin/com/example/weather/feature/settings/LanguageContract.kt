package com.example.weather.feature.settings

import com.example.weather.core.common.mvi.UiEffect
import com.example.weather.core.common.mvi.UiEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.model.AppLanguage

data class LanguageState(
    val selected: AppLanguage = AppLanguage.Default,
) : UiState

sealed interface LanguageEvent : UiEvent {
    data class LanguageSelected(val language: AppLanguage) : LanguageEvent
    data object BackClicked : LanguageEvent
}

sealed interface LanguageEffect : UiEffect {
    data object NavigateBack : LanguageEffect
}
