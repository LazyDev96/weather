package com.example.weather.feature.splash

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.common.mvi.NoEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_DURATION_MS = 1_500L

class SplashViewModel() :
    MviViewModel<SplashState, NoEvent, SplashEffect>(SplashState()) {

    init {
        // Здесь место для реальной инициализации: прогрев БД, проверка авторизации, онбординг.
        viewModelScope.launch {
            delay(SPLASH_DURATION_MS)
            setState { copy(isLoading = false) }
            sendEffect(SplashEffect.NavigateToMain)
        }
    }

    override fun handleEvent(event: NoEvent) = Unit
}
