package com.example.weather.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.AppSettings
import com.example.weather.core.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import okio.IOException

private val LANGUAGE_KEY = stringPreferencesKey("language")
private val THEME_KEY = stringPreferencesKey("theme")
private val WEATHER_NOTIFICATION_KEY = booleanPreferencesKey("weather_notification")

/** Настройки приложения (язык и тема) в Preferences DataStore. */
class SettingsRepository(
    private val dataStore: DataStore<Preferences>,
) {

    /** Всегда эмитит значение: до первой записи это значения по умолчанию. */
    val settings: Flow<AppSettings> = dataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { preferences ->
            AppSettings(
                language = AppLanguage.fromTag(preferences[LANGUAGE_KEY]),
                theme = ThemeMode.fromName(preferences[THEME_KEY]),
                weatherNotificationEnabled = preferences[WEATHER_NOTIFICATION_KEY]
                    ?: AppSettings().weatherNotificationEnabled,
            )
        }
        .distinctUntilChanged()

    suspend fun setLanguage(language: AppLanguage) {
        dataStore.edit { it[LANGUAGE_KEY] = language.tag }
    }

    suspend fun setWeatherNotificationEnabled(enabled: Boolean) {
        dataStore.edit { it[WEATHER_NOTIFICATION_KEY] = enabled }
    }

    suspend fun setTheme(theme: ThemeMode) {
        dataStore.edit { it[THEME_KEY] = theme.name }
    }
}
