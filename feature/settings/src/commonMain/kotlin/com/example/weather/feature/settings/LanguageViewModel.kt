package com.example.weather.feature.settings

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.data.SettingsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val settingsRepository: SettingsRepository,
) : MviViewModel<LanguageState, LanguageEvent, LanguageEffect>(LanguageState()) {

    init {
        settingsRepository.settings
            .onEach { settings -> setState { copy(selected = settings.language) } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: LanguageEvent) {
        when (event) {
            // Язык интерфейса меняет MainActivity, наблюдая за настройками: здесь только запись
            is LanguageEvent.LanguageSelected -> viewModelScope.launch {
                settingsRepository.setLanguage(event.language)
            }
            LanguageEvent.BackClicked -> sendEffect(LanguageEffect.NavigateBack)
        }
    }
}
