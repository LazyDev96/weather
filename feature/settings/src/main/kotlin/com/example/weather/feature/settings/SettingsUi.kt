package com.example.weather.feature.settings

import androidx.annotation.StringRes
import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.ThemeMode

@StringRes
internal fun AppLanguage.labelRes(): Int = when (this) {
    AppLanguage.RUSSIAN -> R.string.settings_language_russian
    AppLanguage.ENGLISH -> R.string.settings_language_english
}

@StringRes
internal fun ThemeMode.labelRes(): Int = when (this) {
    ThemeMode.LIGHT -> R.string.settings_theme_light
    ThemeMode.DARK -> R.string.settings_theme_dark
    ThemeMode.SYSTEM -> R.string.settings_theme_system
}
