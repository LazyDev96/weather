package com.example.weather.core.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Базовая ViewModel для MVI.
 *
 * Поток данных односторонний:
 * UI --[onEvent]--> ViewModel --[setState]--> state --> UI
 *                            \--[sendEffect]--> effects --> UI (один раз)
 *
 * @param S состояние экрана
 * @param E события от UI
 * @param F одноразовые эффекты
 */
abstract class MviViewModel<S : UiState, E : UiEvent, F : UiEffect>(
    initialState: S,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // Channel буферизует эффекты, пока UI не подписан (например, во время поворота экрана),
    // и доставляет каждый эффект ровно одному подписчику.
    private val _effects = Channel<F>(Channel.BUFFERED)
    val effects: Flow<F> = _effects.receiveAsFlow()

    protected val currentState: S
        get() = _state.value

    /** Единственная точка входа для UI. */
    fun onEvent(event: E) {
        handleEvent(event)
    }

    protected abstract fun handleEvent(event: E)

    /** Редьюсер: создаёт новое состояние из текущего. */
    protected fun setState(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }

    protected fun sendEffect(effect: F) {
        viewModelScope.launch { _effects.send(effect) }
    }
}
