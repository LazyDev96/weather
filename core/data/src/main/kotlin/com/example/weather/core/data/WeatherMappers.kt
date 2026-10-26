package com.example.weather.core.data

import com.example.weather.core.data.model.CurrentWeather
import com.example.weather.core.data.model.DailyWeather
import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherCondition
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.database.DailyForecastEntity
import com.example.weather.core.database.WeatherEntity
import com.example.weather.core.database.WeatherWithDaily
import com.example.weather.core.network.model.ForecastResponse
import java.time.LocalDate
import kotlin.math.roundToInt

// DTO -> Room

internal fun ForecastResponse.toCurrentEntity(locationId: String, fetchedAt: Long) = WeatherEntity(
    locationId = locationId,
    temperature = current.temperature,
    apparentTemperature = current.apparentTemperature,
    humidity = current.humidity.roundToInt(),
    weatherCode = current.weatherCode.roundToInt(),
    windSpeed = current.windSpeed,
    fetchedAt = fetchedAt,
)

internal fun ForecastResponse.toDailyEntities(locationId: String): List<DailyForecastEntity> =
    daily.time.indices.map { i ->
        DailyForecastEntity(
            locationId = locationId,
            date = daily.time[i],
            weatherCode = daily.weatherCode[i].roundToInt(),
            temperatureMax = daily.temperatureMax[i],
            temperatureMin = daily.temperatureMin[i],
        )
    }

// Room -> доменная модель

internal fun WeatherWithDaily.toDomain(location: WeatherLocation) = Weather(
    location = location,
    current = CurrentWeather(
        temperature = current.temperature,
        apparentTemperature = current.apparentTemperature,
        humidity = current.humidity,
        windSpeed = current.windSpeed,
        condition = WeatherCondition.fromWmoCode(current.weatherCode),
    ),
    // Порядок элементов @Relation не гарантирован, сортируем по дате (ISO-строки сортируются корректно)
    daily = daily.sortedBy { it.date }.map { day ->
        DailyWeather(
            date = LocalDate.parse(day.date),
            condition = WeatherCondition.fromWmoCode(day.weatherCode),
            temperatureMax = day.temperatureMax,
            temperatureMin = day.temperatureMin,
        )
    },
    fetchedAt = current.fetchedAt,
)
