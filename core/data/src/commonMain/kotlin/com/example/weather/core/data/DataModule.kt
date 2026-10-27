package com.example.weather.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/** Путь к файлу настроек: у каждой платформы своя папка данных приложения. */
class SettingsFilePath(val value: String)

internal expect val dataPlatformModule: Module

val dataModule = module {
    includes(dataPlatformModule)

    single<DataStore<Preferences>> {
        val path = get<SettingsFilePath>().value
        PreferenceDataStoreFactory.createWithPath(
            // Повреждённый файл настроек не должен ронять приложение: начинаем с настроек по умолчанию
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            produceFile = { path.toPath() },
        )
    }

    singleOf(::SettingsRepository)
    singleOf(::WeatherRepository)
    singleOf(::LocationRepository)
    singleOf(::FavoritesRepository)
}
