package com.example.weather.core.data

import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.database.WeatherDao
import com.example.weather.core.network.OpenMeteoApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

private const val CACHE_TTL_MS = 30 * 60 * 1000L

/**
 * Единственный источник правды — Room. UI подписывается на [observe],
 * а [refresh] загружает данные из сети и записывает их в базу.
 */
class WeatherRepository(
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
        val now = Clock.System.now().toEpochMilliseconds()
        val fetchedAt = if (force) null else dao.getFetchedAt(location.id)
        println("WEATHER repo: fetchedAt=$fetchedAt")

        if (fetchedAt == null || now - fetchedAt >= CACHE_TTL_MS) {
            val response = api.getForecast(
                latitude = location.latitude,
                longitude = location.longitude,
            )
            println("WEATHER repo: ответ получен, запись в БД")
            dao.replace(
                current = response.toCurrentEntity(location.id, fetchedAt = now),
                daily = response.toDailyEntities(location.id),
            )
        }
        println("WEATHER repo: готово")
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        // Причину нельзя терять: сеть, парсинг и запись в БД для UI выглядят одинаково.
        // На Android вывод виден в logcat по тегу System.out
        println("WeatherRepository.refresh failed: $e")
        Result.failure(e)
    }
}
