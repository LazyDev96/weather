package com.example.weather.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Ответ Open-Meteo `/v1/forecast`. Описаны только используемые поля,
 * остальные отбрасываются благодаря `ignoreUnknownKeys` в Json.
 */
@Serializable
data class ForecastResponse(
    val current: CurrentDto,
    val daily: DailyDto,
)

// Влажность и weather_code читаем как Double: JSON-тип (3 или 3.0) API не гарантирует,
// а Double принимает оба варианта.
@Serializable
data class CurrentDto(
    @SerialName("temperature_2m") val temperature: Double,
    @SerialName("apparent_temperature") val apparentTemperature: Double,
    @SerialName("relative_humidity_2m") val humidity: Double,
    @SerialName("weather_code") val weatherCode: Double,
    @SerialName("wind_speed_10m") val windSpeed: Double,
)

@Serializable
data class DailyDto(
    val time: List<String>,
    @SerialName("weather_code") val weatherCode: List<Double>,
    @SerialName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerialName("temperature_2m_min") val temperatureMin: List<Double>,
)
