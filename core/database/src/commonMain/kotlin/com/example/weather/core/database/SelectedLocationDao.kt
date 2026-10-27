package com.example.weather.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SelectedLocationDao {

    /** null, пока пользователь не выбрал город. */
    @Query("SELECT * FROM selected_location LIMIT 1")
    fun observe(): Flow<SelectedLocationEntity?>

    // Первичный ключ всегда 1, поэтому REPLACE перезаписывает единственную строку
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: SelectedLocationEntity)
}
