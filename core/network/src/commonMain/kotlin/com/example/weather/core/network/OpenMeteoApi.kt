package com.example.weather.core.network

import com.example.weather.core.network.model.ForecastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val FORECAST_URL = "https://api.open-meteo.com/v1/forecast"
private const val CURRENT_FIELDS =
    "temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m"
private const val DAILY_FIELDS = "weather_code,temperature_2m_max,temperature_2m_min"

/** Open-Meteo: бесплатный API без ключа (для некоммерческого использования). */
class OpenMeteoApi(private val client: HttpClient) {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        current: String = CURRENT_FIELDS,
        daily: String = DAILY_FIELDS,
        timezone: String = "auto",
        forecastDays: Int = 5,
    ): ForecastResponse = client.get(FORECAST_URL) {
        parameter("latitude", latitude)
        parameter("longitude", longitude)
        parameter("current", current)
        parameter("daily", daily)
        parameter("timezone", timezone)
        parameter("forecast_days", forecastDays)
    }.body()
}
