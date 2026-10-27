package com.example.weather.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [
        FavoriteLocationEntity::class,
        WeatherEntity::class,
        DailyForecastEntity::class,
        SelectedLocationEntity::class,
    ],
    version = 4,
    exportSchema = true,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteLocationDao(): FavoriteLocationDao
    abstract fun weatherDao(): WeatherDao
    abstract fun selectedLocationDao(): SelectedLocationDao
}

// Реализации expect-объекта генерирует компилятор Room для каждой платформы
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
