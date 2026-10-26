package com.example.weather.core.network

import com.example.weather.core.network.model.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Open-Meteo Geocoding API (данные GeoNames). Ищет по названию, минимум 3 символа,
 * полного списка городов страны не отдаёт.
 */
interface GeocodingApi {

    @GET("v1/search")
    suspend fun searchPlaces(
        @Query("name") name: String,
        @Query("count") count: Int = 30,
        @Query("language") language: String = "ru",
        @Query("countryCode") countryCode: String = "RU",
    ): GeocodingResponse
}
