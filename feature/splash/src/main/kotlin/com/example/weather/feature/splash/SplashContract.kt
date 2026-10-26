package com.example.weather.feature.splash

import com.example.weather.core.common.mvi.UiEffect
import com.example.weather.core.common.mvi.UiState

data class SplashState(
    val isLoading: Boolean = true,
) : UiState

sealed interface SplashEffect : UiEffect {
    data object NavigateToMain : SplashEffect
}
