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
import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme

@Composable
internal fun LanguageRoute(
    onBack: () -> Unit,
    viewModel: LanguageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            LanguageEffect.NavigateBack -> onBack()
        }
    }

    LanguageScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
internal fun LanguageScreen(
    state: LanguageState,
    onEvent: (LanguageEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        SettingsTopBar(
            title = stringResource(R.string.settings_language),
            onBack = { onEvent(LanguageEvent.BackClicked) },
        )
        AppLanguage.entries.forEach { language ->
            OptionRow(
                label = stringResource(language.labelRes()),
                selected = language == state.selected,
                onClick = { onEvent(LanguageEvent.LanguageSelected(language)) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LanguageScreenPreview() {
    AppTheme { LanguageScreen(state = LanguageState(), onEvent = {}) }
}
