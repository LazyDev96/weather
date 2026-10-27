package com.example.weather.feature.settings

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.data.SettingsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : MviViewModel<SettingsState, SettingsEvent, SettingsEffect>(SettingsState()) {

    init {
        settingsRepository.settings
            .onEach { settings -> setState { copy(settings = settings) } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.LanguageClicked -> sendEffect(SettingsEffect.OpenLanguage)
            SettingsEvent.ThemeClicked -> sendEffect(SettingsEffect.OpenTheme)
            // Службу запускает и останавливает MainActivity, наблюдая за настройкой
            is SettingsEvent.WeatherNotificationToggled -> viewModelScope.launch {
                settingsRepository.setWeatherNotificationEnabled(event.enabled)
            }
        }
    }
}
