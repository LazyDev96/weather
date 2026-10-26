package com.example.weather.feature.home

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.data.FavoritesRepository
import com.example.weather.core.data.LocationRepository
import com.example.weather.core.data.WeatherRepository
import com.example.weather.core.data.model.WeatherLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val favoritesRepository: FavoritesRepository,
) : MviViewModel<HomeState, HomeEvent, HomeEffect>(HomeState()) {

    private var location: WeatherLocation = WeatherLocation.Default
    private var refreshJob: Job? = null

    init {
        observeSelectedLocation()
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.RefreshClicked -> refresh(force = true)
            HomeEvent.FavoriteClicked -> toggleFavorite()
        }
    }

    /**
     * Город выбирается на вкладке Explore и хранится в Room. При каждой смене города
     * подписка на кэш переключается на новый город (collectLatest отменяет предыдущую),
     * а погода обновляется из сети, если кэш устарел.
     */
    private fun observeSelectedLocation() {
        viewModelScope.launch {
            locationRepository.observeSelected()
                .distinctUntilChanged()
                .collectLatest { selected ->
                    location = selected
                    setState { copy(hasError = false) }
                    refresh(force = false)

                    // Звёздочка следует за городом: подписка отменяется вместе с collectLatest
                    launch {
                        favoritesRepository.observeIsFavorite(selected.id).collect { isFavorite ->
                            setState { copy(isFavorite = isFavorite) }
                        }
                    }

                    // Кэш из Room: показывается сразу, в том числе офлайн
                    weatherRepository.observe(selected).collect { weather ->
                        setState { copy(weather = weather) }
                    }
                }
        }
    }

    private fun toggleFavorite() {
        val target = location
        viewModelScope.launch {
            if (currentState.isFavorite) {
                favoritesRepository.remove(target.id)
            } else {
                favoritesRepository.add(target)
            }
        }
    }

    private fun refresh(force: Boolean) {
        val target = location

        // Новый запрос (в том числе для другого города) заменяет незавершённый
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            setState { copy(isRefreshing = true) }
            val result = weatherRepository.refresh(target, force)
            setState { copy(isRefreshing = false, hasError = result.isFailure) }

            // Обратная связь нужна только на действие пользователя, не на фоновую загрузку
            if (force && result.isFailure) {
                sendEffect(HomeEffect.RefreshFailed)
            }
        }
    }
}
