package com.example.weather.core.network

import com.example.weather.core.network.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val CURRENT_FIELDS =
    "temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m"
private const val DAILY_FIELDS = "weather_code,temperature_2m_max,temperature_2m_min"

/** Open-Meteo: бесплатный API без ключа (для некоммерческого использования). */
interface OpenMeteoApi {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = CURRENT_FIELDS,
        @Query("daily") daily: String = DAILY_FIELDS,
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 5,
    ): ForecastResponse
}
