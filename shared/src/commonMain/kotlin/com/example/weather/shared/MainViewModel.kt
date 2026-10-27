package com.example.weather.shared

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.common.mvi.NoEffect
import com.example.weather.core.common.mvi.NoEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.SettingsRepository
import com.example.weather.core.data.model.AppSettings
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/** [settings] == null, пока настройки не загружены с диска. */
data class MainState(
    val settings: AppSettings? = null,
) : UiState

/** Держит настройки на уровне всего приложения: по ним выбираются тема и язык. */
class MainViewModel(
    settingsRepository: SettingsRepository,
) : MviViewModel<MainState, NoEvent, NoEffect>(MainState()) {

    init {
        settingsRepository.settings
            .onEach { settings -> setState { copy(settings = settings) } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: NoEvent) = Unit
}
