package com.example.weather.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.ThemeMode
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme
import com.example.weather.feature.settings.resources.Res
import com.example.weather.feature.settings.resources.settings_theme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ThemeRoute(
    onBack: () -> Unit,
    viewModel: ThemeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            ThemeEffect.NavigateBack -> onBack()
        }
    }

    ThemeScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
internal fun ThemeScreen(
    state: ThemeState,
    onEvent: (ThemeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        SettingsTopBar(
            title = stringResource(Res.string.settings_theme),
            onBack = { onEvent(ThemeEvent.BackClicked) },
        )
        ThemeMode.entries.forEach { theme ->
            OptionRow(
                label = stringResource(theme.labelRes()),
                selected = theme == state.selected,
                onClick = { onEvent(ThemeEvent.ThemeSelected(theme)) },
            )
        }
    }
}

@Preview
@Composable
private fun ThemeScreenPreview() {
    AppTheme { ThemeScreen(state = ThemeState(selected = ThemeMode.DARK), onEvent = {}) }
}
