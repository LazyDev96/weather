package com.example.weather.feature.home

import com.example.weather.core.data.model.WeatherCondition
import com.example.weather.feature.home.resources.Res
import com.example.weather.feature.home.resources.home_day_sunday
import com.example.weather.feature.home.resources.home_day_saturday
import com.example.weather.feature.home.resources.home_day_friday
import com.example.weather.feature.home.resources.home_day_thursday
import com.example.weather.feature.home.resources.home_day_wednesday
import com.example.weather.feature.home.resources.home_day_tuesday
import com.example.weather.feature.home.resources.home_day_monday
import com.example.weather.feature.home.resources.home_condition_clear
import com.example.weather.feature.home.resources.home_condition_cloudy
import com.example.weather.feature.home.resources.home_condition_drizzle
import com.example.weather.feature.home.resources.home_condition_fog
import com.example.weather.feature.home.resources.home_condition_partly_cloudy
import com.example.weather.feature.home.resources.home_condition_rain
import com.example.weather.feature.home.resources.home_condition_snow
import com.example.weather.feature.home.resources.home_condition_thunderstorm
import com.example.weather.feature.home.resources.home_condition_unknown
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.StringResource

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

internal fun WeatherCondition.labelRes(): StringResource = when (this) {
    WeatherCondition.CLEAR -> Res.string.home_condition_clear
    WeatherCondition.PARTLY_CLOUDY -> Res.string.home_condition_partly_cloudy
    WeatherCondition.CLOUDY -> Res.string.home_condition_cloudy
    WeatherCondition.FOG -> Res.string.home_condition_fog
    WeatherCondition.DRIZZLE -> Res.string.home_condition_drizzle
    WeatherCondition.RAIN -> Res.string.home_condition_rain
    WeatherCondition.SNOW -> Res.string.home_condition_snow
    WeatherCondition.THUNDERSTORM -> Res.string.home_condition_thunderstorm
    WeatherCondition.UNKNOWN -> Res.string.home_condition_unknown
}

/** Название дня недели: java.time.format.TextStyle недоступен в общем коде, поэтому строки в ресурсах. */
internal fun DayOfWeek.labelRes(): StringResource = when (this) {
    DayOfWeek.MONDAY -> Res.string.home_day_monday
    DayOfWeek.TUESDAY -> Res.string.home_day_tuesday
    DayOfWeek.WEDNESDAY -> Res.string.home_day_wednesday
    DayOfWeek.THURSDAY -> Res.string.home_day_thursday
    DayOfWeek.FRIDAY -> Res.string.home_day_friday
    DayOfWeek.SATURDAY -> Res.string.home_day_saturday
    else -> Res.string.home_day_sunday
}
