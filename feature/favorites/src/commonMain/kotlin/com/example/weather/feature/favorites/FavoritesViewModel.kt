package com.example.weather.feature.favorites

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.data.FavoritesRepository
import com.example.weather.core.data.LocationRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val locationRepository: LocationRepository,
) : MviViewModel<FavoritesState, FavoritesEvent, FavoritesEffect>(FavoritesState()) {

    init {
        // Room сам эмитит новый список при каждом изменении таблицы
        favoritesRepository.observeAll()
            .onEach { items -> setState { copy(items = items, isLoading = false) } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.LocationClicked -> viewModelScope.launch {
                locationRepository.select(event.location)
                sendEffect(FavoritesEffect.LocationSelected)
            }
            is FavoritesEvent.RemoveClicked -> viewModelScope.launch {
                favoritesRepository.remove(event.locationId)
            }
        }
    }
}
