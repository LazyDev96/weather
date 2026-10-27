package com.example.weather.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteLocationDao {

    /** Новые города сверху. */
    @Query("SELECT * FROM favorite_locations ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<FavoriteLocationEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_locations WHERE locationId = :locationId)")
    fun observeExists(locationId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: FavoriteLocationEntity)

    @Query("DELETE FROM favorite_locations WHERE locationId = :locationId")
    suspend fun deleteById(locationId: String)
}
