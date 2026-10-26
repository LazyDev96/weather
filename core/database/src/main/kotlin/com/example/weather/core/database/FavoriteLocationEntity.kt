package com.example.weather.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Город в избранном. Ключ — идентификатор города, поэтому повторно добавить один город нельзя. */
@Entity(tableName = "favorite_locations")
data class FavoriteLocationEntity(
    @PrimaryKey val locationId: String,
    val name: String,
    val region: String?,
    val latitude: Double,
    val longitude: Double,
    val addedAt: Long,
)
