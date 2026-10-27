package com.example.weather.feature.settings

import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.ThemeMode
import com.example.weather.feature.settings.resources.Res
import com.example.weather.feature.settings.resources.settings_language_english
import com.example.weather.feature.settings.resources.settings_language_russian
import com.example.weather.feature.settings.resources.settings_theme_dark
import com.example.weather.feature.settings.resources.settings_theme_light
import com.example.weather.feature.settings.resources.settings_theme_system
import org.jetbrains.compose.resources.StringResource

internal fun AppLanguage.labelRes(): StringResource = when (this) {
    AppLanguage.RUSSIAN -> Res.string.settings_language_russian
    AppLanguage.ENGLISH -> Res.string.settings_language_english
}

internal fun ThemeMode.labelRes(): StringResource = when (this) {
    ThemeMode.LIGHT -> Res.string.settings_theme_light
    ThemeMode.DARK -> Res.string.settings_theme_dark
    ThemeMode.SYSTEM -> Res.string.settings_theme_system
}
