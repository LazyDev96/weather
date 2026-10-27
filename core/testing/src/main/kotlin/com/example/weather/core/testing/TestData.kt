package com.example.weather.core.testing

import com.example.weather.core.data.model.CurrentWeather
import com.example.weather.core.data.model.DailyWeather
import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherCondition
import com.example.weather.core.data.model.WeatherLocation
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

object TestData {

    val moscow: WeatherLocation = WeatherLocation.Default

    val kazan = WeatherLocation(
        id = "551487",
        name = "Kazan",
        latitude = 55.79,
        longitude = 49.12,
        region = "Tatarstan",
    )

    val samara = WeatherLocation(
        id = "499099",
        name = "Samara",
        latitude = 53.2,
        longitude = 50.15,
        region = "Samara Oblast",
    )

    fun weather(location: WeatherLocation = moscow, temperature: Double = 5.0) = Weather(
        location = location,
        current = CurrentWeather(
            temperature = temperature,
            apparentTemperature = temperature - 3,
            humidity = 80,
            windSpeed = 12.0,
            condition = WeatherCondition.CLOUDY,
        ),
        daily = List(5) { index ->
            DailyWeather(
                date = LocalDate(2026, 10, 26).plus(index, DateTimeUnit.DAY),
                condition = WeatherCondition.CLOUDY,
                temperatureMax = temperature + 2,
                temperatureMin = temperature - 4,
            )
        },
        fetchedAt = 0L,
    )
}
