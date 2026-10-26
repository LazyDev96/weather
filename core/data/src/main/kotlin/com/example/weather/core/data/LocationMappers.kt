package com.example.weather.core.data

import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.database.FavoriteLocationEntity
import com.example.weather.core.database.SelectedLocationEntity
import com.example.weather.core.network.model.PlaceDto

internal fun PlaceDto.toDomain() = WeatherLocation(
    id = id.toString(),
    name = name,
    latitude = latitude,
    longitude = longitude,
    region = admin1,
)

/** Оставляем только населённые пункты (PPL, PPLA, PPLC...), отбрасывая регионы, горы и т.п. */
internal fun PlaceDto.isPopulatedPlace(): Boolean = featureCode?.startsWith("PPL") == true

internal fun SelectedLocationEntity.toDomain() = WeatherLocation(
    id = locationId,
    name = name,
    latitude = latitude,
    longitude = longitude,
    region = region,
)

internal fun WeatherLocation.toEntity() = SelectedLocationEntity(
    locationId = id,
    name = name,
    region = region,
    latitude = latitude,
    longitude = longitude,
)

internal fun FavoriteLocationEntity.toDomain() = WeatherLocation(
    id = locationId,
    name = name,
    latitude = latitude,
    longitude = longitude,
    region = region,
)

internal fun WeatherLocation.toFavoriteEntity(addedAt: Long) = FavoriteLocationEntity(
    locationId = id,
    name = name,
    region = region,
    latitude = latitude,
    longitude = longitude,
    addedAt = addedAt,
)
