package com.example.weather.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import com.example.weather.core.common.mvi.UiEffect
import kotlinx.coroutines.flow.Flow

/**
 * Подписывается на одноразовые эффекты ViewModel, пока composable находится в композиции.
 * Актуальная версия [onEffect] используется без перезапуска подписки.
 */
@Composable
fun <F : UiEffect> CollectEffects(
    effects: Flow<F>,
    onEffect: suspend (F) -> Unit,
) {
    val currentOnEffect by rememberUpdatedState(onEffect)
    LaunchedEffect(effects) {
        effects.collect { currentOnEffect(it) }
    }
}
