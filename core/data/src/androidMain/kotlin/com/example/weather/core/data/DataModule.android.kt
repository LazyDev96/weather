package com.example.weather.core.data

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val dataPlatformModule: Module = module {
    // Тот же файл, что раньше создавал preferencesDataStoreFile("settings"): настройки сохраняются
    single {
        SettingsFilePath(
            androidContext().filesDir.resolve("datastore/settings.preferences_pb").absolutePath,
        )
    }
}
