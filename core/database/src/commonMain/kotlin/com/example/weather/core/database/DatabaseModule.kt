package com.example.weather.core.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Платформа поставляет `RoomDatabase.Builder<AppDatabase>` (путь к файлу БД у Android и iOS разный),
 * а остальная настройка общая.
 */
internal expect val databasePlatformModule: Module

val databaseModule = module {
    includes(databasePlatformModule)

    single<AppDatabase> {
        get<RoomDatabase.Builder<AppDatabase>>()
            // Одна и та же версия SQLite на обеих платформах
            .setDriver(BundledSQLiteDriver())
            .addMigrations(*Migrations.all)
            .build()
    }

    single { get<AppDatabase>().weatherDao() }
    single { get<AppDatabase>().selectedLocationDao() }
    single { get<AppDatabase>().favoriteLocationDao() }
}
