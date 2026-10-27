package com.example.weather.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.AppSettings
import com.example.weather.core.data.model.ThemeMode
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.platform.isWeatherNotificationSupported
import com.example.weather.core.ui.theme.AppTheme
import com.example.weather.feature.settings.resources.Res
import com.example.weather.feature.settings.resources.settings_language
import com.example.weather.feature.settings.resources.settings_theme
import com.example.weather.feature.settings.resources.settings_title
import com.example.weather.feature.settings.resources.settings_weather_notification
import com.example.weather.feature.settings.resources.settings_weather_notification_hint
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingsRoute(
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            SettingsEffect.OpenLanguage -> onLanguageClick()
            SettingsEffect.OpenTheme -> onThemeClick()
        }
    }

    SettingsScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
internal fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(Res.string.settings_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp),
        )
        SettingsItem(
            title = stringResource(Res.string.settings_language),
            value = stringResource(state.settings.language.labelRes()),
            onClick = { onEvent(SettingsEvent.LanguageClicked) },
        )
        SettingsItem(
            title = stringResource(Res.string.settings_theme),
            value = stringResource(state.settings.theme.labelRes()),
            onClick = { onEvent(SettingsEvent.ThemeClicked) },
        )
        // Постоянное уведомление реализовано только на Android (foreground service)
        if (isWeatherNotificationSupported) {
            ListItem(
                headlineContent = { Text(stringResource(Res.string.settings_weather_notification)) },
                supportingContent = { Text(stringResource(Res.string.settings_weather_notification_hint)) },
                trailingContent = {
                    Switch(
                        checked = state.settings.weatherNotificationEnabled,
                        onCheckedChange = null,
                    )
                },
                modifier = Modifier.toggleable(
                    value = state.settings.weatherNotificationEnabled,
                    role = Role.Switch,
                    onValueChange = { onEvent(SettingsEvent.WeatherNotificationToggled(it)) },
                ),
            )
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    value: String,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(value) },
        trailingContent = {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        },
        modifier = Modifier.clickable(onClick = onClick),
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen(
            state = SettingsState(AppSettings(language = AppLanguage.ENGLISH, theme = ThemeMode.SYSTEM)),
            onEvent = {},
        )
    }
}
