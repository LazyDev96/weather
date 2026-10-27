package com.example.weather.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private const val DATABASE_NAME = "weather.db"

internal actual val databasePlatformModule: Module = module {
    single<RoomDatabase.Builder<AppDatabase>> { databaseBuilder(androidContext()) }
}

private fun databaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = appContext.getDatabasePath(DATABASE_NAME).absolutePath,
    ).setQueryCoroutineContext(Dispatchers.IO)
}
