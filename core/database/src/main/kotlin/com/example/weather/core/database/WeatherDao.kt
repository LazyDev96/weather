package com.example.weather.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

// abstract class, а не interface: метод replace с телом и @Transaction
@Dao
abstract class WeatherDao {

    /** Эмитит null, пока кэш пуст, и новое значение после каждой записи. */
    @Transaction
    @Query("SELECT * FROM weather WHERE locationId = :locationId")
    abstract fun observe(locationId: String): Flow<WeatherWithDaily?>

    @Query("SELECT fetchedAt FROM weather WHERE locationId = :locationId")
    abstract suspend fun getFetchedAt(locationId: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCurrent(entity: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDaily(entities: List<DailyForecastEntity>)

    @Query("DELETE FROM weather_daily WHERE locationId = :locationId")
    abstract suspend fun deleteDaily(locationId: String)

    /** Атомарно заменяет кэш локации: подписчики увидят либо старые, либо новые данные целиком. */
    @Transaction
    open suspend fun replace(current: WeatherEntity, daily: List<DailyForecastEntity>) {
        insertCurrent(current)
        deleteDaily(current.locationId)
        insertDaily(daily)
    }
}
