package com.example.weather.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Город, для которого показывается погода. В таблице всегда не больше одной строки. */
@Entity(tableName = "selected_location")
data class SelectedLocationEntity(
    @PrimaryKey val id: Int = 1,
    val locationId: String,
    val name: String,
    val region: String?,
    val latitude: Double,
    val longitude: Double,
)
