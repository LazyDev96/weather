package com.example.weather.core.data

import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.database.FavoriteLocationDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

/** Избранные города. Хранятся в Room, поэтому доступны без сети. */
class FavoritesRepository(
    private val dao: FavoriteLocationDao,
) {

    fun observeAll(): Flow<List<WeatherLocation>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    fun observeIsFavorite(locationId: String): Flow<Boolean> = dao.observeExists(locationId)

    suspend fun add(location: WeatherLocation) {
        dao.upsert(location.toFavoriteEntity(addedAt = Clock.System.now().toEpochMilliseconds()))
    }

    suspend fun remove(locationId: String) {
        dao.deleteById(locationId)
    }
}
