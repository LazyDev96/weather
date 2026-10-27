package com.example.weather.feature.explore

import androidx.lifecycle.viewModelScope
import com.example.weather.core.common.mvi.MviViewModel
import com.example.weather.core.data.LocationRepository
import com.example.weather.core.data.model.WeatherLocation
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val MIN_QUERY_LENGTH = 3
private const val SEARCH_DEBOUNCE_MS = 400L

class ExploreViewModel(
    private val repository: LocationRepository,
) : MviViewModel<ExploreState, ExploreEvent, ExploreEffect>(ExploreState()) {

    private val queries = MutableStateFlow("")

    init {
        observeSelectedLocation()
        observeQueries()
    }

    override fun handleEvent(event: ExploreEvent) {
        when (event) {
            is ExploreEvent.QueryChanged -> {
                setState { copy(query = event.query) }
                queries.value = event.query
            }
            is ExploreEvent.ResultClicked -> select(event.location)
            ExploreEvent.RetryClicked -> viewModelScope.launch { search(currentState.query.trim()) }
        }
    }

    private fun observeSelectedLocation() {
        repository.observeSelected()
            .onEach { selected -> setState { copy(selectedId = selected.id) } }
            .launchIn(viewModelScope)
    }

    // Запрос уходит в сеть через 400 мс после последнего символа; collectLatest отменяет устаревший поиск
    @OptIn(FlowPreview::class)
    private fun observeQueries() {
        viewModelScope.launch {
            queries
                .debounce(SEARCH_DEBOUNCE_MS)
                .map { it.trim() }
                .distinctUntilChanged()
                .collectLatest { query -> search(query) }
        }
    }

    private suspend fun search(query: String) {
        if (query.length < MIN_QUERY_LENGTH) {
            setState { copy(results = emptyList(), status = SearchStatus.IDLE) }
            return
        }

        setState { copy(status = SearchStatus.LOADING) }
        repository.search(query)
            .onSuccess { found -> setState { copy(results = found, status = SearchStatus.SUCCESS) } }
            .onFailure { setState { copy(results = emptyList(), status = SearchStatus.ERROR) } }
    }

    private fun select(location: WeatherLocation) {
        viewModelScope.launch {
            repository.select(location)
            sendEffect(ExploreEffect.NavigateToHome)
        }
    }
}
