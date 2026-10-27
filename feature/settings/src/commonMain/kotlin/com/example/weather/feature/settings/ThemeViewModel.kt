package com.example.weather.feature.settings

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.data.SettingsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val settingsRepository: SettingsRepository,
) : MviViewModel<ThemeState, ThemeEvent, ThemeEffect>(ThemeState()) {

    init {
        settingsRepository.settings
            .onEach { settings -> setState { copy(selected = settings.theme) } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: ThemeEvent) {
        when (event) {
            // Тему применяет MainActivity, наблюдая за настройками: здесь только запись
            is ThemeEvent.ThemeSelected -> viewModelScope.launch {
                settingsRepository.setTheme(event.theme)
            }
            ThemeEvent.BackClicked -> sendEffect(ThemeEffect.NavigateBack)
        }
    }
}
