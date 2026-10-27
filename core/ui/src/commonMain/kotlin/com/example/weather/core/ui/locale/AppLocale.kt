package com.example.weather.core.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf

/** Платформенная часть переключения языка приложения без смены языка системы. */
expect object LocalAppLocale {
    val current: String
        @Composable get

    @Composable
    infix fun provides(value: String?): ProvidedValue<*>
}

// Статический local при смене значения перерисовывает всё содержимое, но не сбрасывает его состояние
// (навигацию, прокрутку, ввод). Именно перерисовка заставляет stringResource перечитать язык.
private val LocalLanguageTag = staticCompositionLocalOf { "" }

/**
 * Применяет язык приложения ([languageTag], например "ru" или "en") ко всему [content].
 * Android: Locale.setDefault и конфигурация ресурсов; iOS: ключ AppleLanguages в NSUserDefaults.
 */
@Composable
fun ProvideAppLocale(languageTag: String, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppLocale provides languageTag,
        LocalLanguageTag provides languageTag,
    ) {
        content()
    }
}
