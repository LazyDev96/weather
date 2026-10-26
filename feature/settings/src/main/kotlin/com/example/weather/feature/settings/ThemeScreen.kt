package com.example.weather.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.ThemeMode
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme

@Composable
internal fun ThemeRoute(
    onBack: () -> Unit,
    viewModel: ThemeViewModel = hiltViewModel(),
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
            title = stringResource(R.string.settings_theme),
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

@Preview(showBackground = true)
@Composable
private fun ThemeScreenPreview() {
    AppTheme { ThemeScreen(state = ThemeState(selected = ThemeMode.DARK), onEvent = {}) }
}
