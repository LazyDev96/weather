package com.example.weather.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.weather.core.database.AppDatabase
import com.example.weather.core.database.FavoriteLocationDao
import com.example.weather.core.database.Migrations
import com.example.weather.core.database.SelectedLocationDao
import com.example.weather.core.database.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "weather.db")
            .addMigrations(Migrations.MIGRATION_1_2, Migrations.MIGRATION_2_3, Migrations.MIGRATION_3_4)
            .build()

    @Provides
    fun provideFavoriteLocationDao(database: AppDatabase): FavoriteLocationDao =
        database.favoriteLocationDao()

    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao = database.weatherDao()

    @Provides
    fun provideSelectedLocationDao(database: AppDatabase): SelectedLocationDao =
        database.selectedLocationDao()
}
