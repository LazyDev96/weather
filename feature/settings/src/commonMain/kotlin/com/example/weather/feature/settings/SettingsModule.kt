package com.example.weather.feature.settings

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::LanguageViewModel)
    viewModelOf(::ThemeViewModel)
}
