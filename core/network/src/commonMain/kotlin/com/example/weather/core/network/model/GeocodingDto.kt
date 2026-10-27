package com.example.weather.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Ответ Open-Meteo Geocoding API. Если ничего не найдено, поля `results` в ответе нет. */
@Serializable
data class GeocodingResponse(
    val results: List<PlaceDto>? = null,
)

@Serializable
data class PlaceDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    /** Регион (область, край, республика). */
    val admin1: String? = null,
    /** Тип объекта GeoNames: PPL, PPLA, PPLC и т.д. для населённых пунктов, ADM1 для регионов. */
    @SerialName("feature_code") val featureCode: String? = null,
)
