package com.example.weather.core.network

import com.example.weather.core.network.model.GeocodingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search"

/**
 * Open-Meteo Geocoding API (данные GeoNames). Ищет по названию, минимум 3 символа,
 * полного списка городов страны не отдаёт.
 */
class GeocodingApi(private val client: HttpClient) {

    suspend fun searchPlaces(
        name: String,
        count: Int = 30,
        language: String = "ru",
        countryCode: String = "RU",
    ): GeocodingResponse = client.get(GEOCODING_URL) {
        parameter("name", name)
        parameter("count", count)
        parameter("language", language)
        parameter("countryCode", countryCode)
    }.body()
}
