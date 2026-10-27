package com.example.weather.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme
import com.example.weather.feature.settings.resources.Res
import com.example.weather.feature.settings.resources.settings_language
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LanguageRoute(
    onBack: () -> Unit,
    viewModel: LanguageViewModel = koinViewModel(),
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
            title = stringResource(Res.string.settings_language),
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

@Preview
@Composable
private fun LanguageScreenPreview() {
    AppTheme { LanguageScreen(state = LanguageState(), onEvent = {}) }
}
