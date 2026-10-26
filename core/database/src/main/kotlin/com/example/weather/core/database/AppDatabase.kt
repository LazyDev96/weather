package com.example.weather.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

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
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteLocationDao(): FavoriteLocationDao
    abstract fun weatherDao(): WeatherDao
    abstract fun selectedLocationDao(): SelectedLocationDao
}
