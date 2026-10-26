package com.example.weather.core.data

import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.database.WeatherDao
import com.example.weather.core.network.OpenMeteoApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val CACHE_TTL_MS = 30 * 60 * 1000L

/**
 * Единственный источник правды — Room. UI подписывается на [observe],
 * а [refresh] загружает данные из сети и записывает их в базу.
 */
@Singleton
class WeatherRepository @Inject constructor(
    private val api: OpenMeteoApi,
    private val dao: WeatherDao,
) {

    /** null, пока в кэше ничего нет. */
    fun observe(location: WeatherLocation): Flow<Weather?> =
        dao.observe(location.id).map { cached -> cached?.toDomain(location) }

    /**
     * Обновляет кэш. Без [force] ничего не делает, если данные свежее 30 минут.
     * Ошибки возвращаются как [Result.failure], кэш при этом остаётся нетронутым.
     */
    suspend fun refresh(location: WeatherLocation, force: Boolean = false): Result<Unit> = try {
        val now = System.currentTimeMillis()
        val fetchedAt = if (force) null else dao.getFetchedAt(location.id)

        if (fetchedAt == null || now - fetchedAt >= CACHE_TTL_MS) {
            val response = api.getForecast(
                latitude = location.latitude,
                longitude = location.longitude,
            )
            dao.replace(
                current = response.toCurrentEntity(location.id, fetchedAt = now),
                daily = response.toDailyEntities(location.id),
            )
        }
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}
