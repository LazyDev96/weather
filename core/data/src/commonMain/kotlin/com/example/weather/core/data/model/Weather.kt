package com.example.weather.core.data.model

import kotlinx.datetime.LocalDate

data class WeatherLocation(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    /** Регион (область, край), помогает отличить одноимённые города. */
    val region: String? = null,
) {
    companion object {
        /** Город по умолчанию. Замените или сделайте выбор города в UI. */
        val Default = WeatherLocation(
            id = "moscow",
            name = "Moscow",
            latitude = 55.7558,
            longitude = 37.6173,
        )
    }
}

data class Weather(
    val location: WeatherLocation,
    val current: CurrentWeather,
    val daily: List<DailyWeather>,
    /** Время последней успешной загрузки (epoch millis). */
    val fetchedAt: Long,
)

data class CurrentWeather(
    val temperature: Double,
    val apparentTemperature: Double,
    val humidity: Int,
    /** км/ч */
    val windSpeed: Double,
    val condition: WeatherCondition,
)

data class DailyWeather(
    val date: LocalDate,
    val condition: WeatherCondition,
    val temperatureMax: Double,
    val temperatureMin: Double,
)

enum class WeatherCondition {
    CLEAR,
    PARTLY_CLOUDY,
    CLOUDY,
    FOG,
    DRIZZLE,
    RAIN,
    SNOW,
    THUNDERSTORM,
    UNKNOWN,
    ;

    companion object {
        /** Коды WMO из документации Open-Meteo. */
        fun fromWmoCode(code: Int): WeatherCondition = when (code) {
            0 -> CLEAR
            1, 2 -> PARTLY_CLOUDY
            3 -> CLOUDY
            45, 48 -> FOG
            in 51..57 -> DRIZZLE
            in 61..67 -> RAIN
            in 71..77 -> SNOW
            in 80..82 -> RAIN
            85, 86 -> SNOW
            in 95..99 -> THUNDERSTORM
            else -> UNKNOWN
        }
    }
}
