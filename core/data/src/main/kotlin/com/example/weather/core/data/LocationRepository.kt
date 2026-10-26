package com.example.weather.core.data

import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.database.SelectedLocationDao
import com.example.weather.core.network.GeocodingApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class LocationRepository @Inject constructor(
    private val geocodingApi: GeocodingApi,
    private val dao: SelectedLocationDao,
) {

    /** Выбранный город; пока пользователь ничего не выбрал, это [WeatherLocation.Default]. */
    fun observeSelected(): Flow<WeatherLocation> =
        dao.observe().map { saved -> saved?.toDomain() ?: WeatherLocation.Default }

    suspend fun select(location: WeatherLocation) {
        dao.upsert(location.toEntity())
    }

    /** Поиск российских населённых пунктов по названию (от 3 символов). */
    suspend fun search(query: String): Result<List<WeatherLocation>> = try {
        val places = geocodingApi.searchPlaces(name = query).results.orEmpty()
        Result.success(places.filter { it.isPopulatedPlace() }.map { it.toDomain() })
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}
