package com.example.weather.core.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

/** Текущая погода: одна строка на локацию. */
@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val locationId: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val humidity: Int,
    val weatherCode: Int,
    val windSpeed: Double,
    val fetchedAt: Long,
)

/** Прогноз по дням: по строке на каждый день локации. */
@Entity(tableName = "weather_daily", primaryKeys = ["locationId", "date"])
data class DailyForecastEntity(
    val locationId: String,
    /** ISO-дата, например 2026-10-25 */
    val date: String,
    val weatherCode: Int,
    val temperatureMax: Double,
    val temperatureMin: Double,
)

/** Текущая погода вместе с прогнозом, читается одним согласованным запросом. */
data class WeatherWithDaily(
    @Embedded val current: WeatherEntity,
    @Relation(parentColumn = "locationId", entityColumn = "locationId")
    val daily: List<DailyForecastEntity>,
)
