package com.example.weather.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.ThemeMode
import com.example.weather.core.ui.locale.ProvideAppLocale
import com.example.weather.core.ui.theme.AppTheme
import com.example.weather.shared.navigation.AppNavHost
import org.koin.compose.viewmodel.koinViewModel

/**
 * Корень общего UI для Android и iOS.
 *
 * @param onDarkThemeResolved сообщает платформе действующую тему (выбранную в настройках, а не системную),
 * чтобы Android мог перекрасить иконки статус-бара.
 */
@Composable
fun App(
    onDarkThemeResolved: (Boolean) -> Unit = {},
    viewModel: MainViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // Пока настройки не загружены, ничего не рисуем: на Android это время держит системный splash
    val settings = state.settings ?: return

    val darkTheme = when (settings.theme) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    LaunchedEffect(darkTheme) { onDarkThemeResolved(darkTheme) }

    ProvideAppLocale(languageTag = settings.language.tag) {
        AppTheme(darkTheme = darkTheme) {
            AppNavHost()
        }
    }
}
