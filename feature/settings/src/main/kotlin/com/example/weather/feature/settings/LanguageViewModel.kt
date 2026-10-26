package com.example.weather.feature.settings

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class LanguageViewModel @Inject constructor(
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
