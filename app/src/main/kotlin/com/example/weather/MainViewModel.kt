package com.example.weather

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.common.mvi.NoEffect
import com.example.weather.core.common.mvi.NoEvent
import com.example.weather.core.common.mvi.UiState
import com.example.weather.core.data.SettingsRepository
import com.example.weather.core.data.model.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/** [settings] == null, пока настройки не загружены с диска. */
data class MainState(
    val settings: AppSettings? = null,
) : UiState

/** Держит настройки на уровне Activity: по ним выбираются тема и язык всего приложения. */
@HiltViewModel
class MainViewModel @Inject constructor(
    settingsRepository: SettingsRepository,
) : MviViewModel<MainState, NoEvent, NoEffect>(MainState()) {

    init {
        settingsRepository.settings
            .onEach { settings -> setState { copy(settings = settings) } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: NoEvent) = Unit
}
