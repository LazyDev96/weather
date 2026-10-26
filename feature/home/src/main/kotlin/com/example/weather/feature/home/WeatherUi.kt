package com.example.weather.feature.home

import androidx.annotation.StringRes
import com.example.weather.core.data.model.WeatherCondition

internal fun WeatherCondition.emoji(): String = when (this) {
    WeatherCondition.CLEAR -> "☀️"
    WeatherCondition.PARTLY_CLOUDY -> "⛅"
    WeatherCondition.CLOUDY -> "☁️"
    WeatherCondition.FOG -> "🌫️"
    WeatherCondition.DRIZZLE -> "🌦️"
    WeatherCondition.RAIN -> "🌧️"
    WeatherCondition.SNOW -> "❄️"
    WeatherCondition.THUNDERSTORM -> "⛈️"
    WeatherCondition.UNKNOWN -> "🌡️"
}

@StringRes
internal fun WeatherCondition.labelRes(): Int = when (this) {
    WeatherCondition.CLEAR -> R.string.home_condition_clear
    WeatherCondition.PARTLY_CLOUDY -> R.string.home_condition_partly_cloudy
    WeatherCondition.CLOUDY -> R.string.home_condition_cloudy
    WeatherCondition.FOG -> R.string.home_condition_fog
    WeatherCondition.DRIZZLE -> R.string.home_condition_drizzle
    WeatherCondition.RAIN -> R.string.home_condition_rain
    WeatherCondition.SNOW -> R.string.home_condition_snow
    WeatherCondition.THUNDERSTORM -> R.string.home_condition_thunderstorm
    WeatherCondition.UNKNOWN -> R.string.home_condition_unknown
}
