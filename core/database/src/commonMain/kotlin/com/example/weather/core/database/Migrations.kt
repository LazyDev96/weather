package com.example.weather.core.database

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

// SQL в миграциях должен в точности соответствовать сущностям (сверяйтесь с schemas/*.json).
object Migrations {

    /** v1 -> v2: таблицы кэша погоды. */
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL(
                "CREATE TABLE IF NOT EXISTS `weather` (" +
                    "`locationId` TEXT NOT NULL, " +
                    "`temperature` REAL NOT NULL, " +
                    "`apparentTemperature` REAL NOT NULL, " +
                    "`humidity` INTEGER NOT NULL, " +
                    "`weatherCode` INTEGER NOT NULL, " +
                    "`windSpeed` REAL NOT NULL, " +
                    "`fetchedAt` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`locationId`))",
            )
            connection.execSQL(
                "CREATE TABLE IF NOT EXISTS `weather_daily` (" +
                    "`locationId` TEXT NOT NULL, " +
                    "`date` TEXT NOT NULL, " +
                    "`weatherCode` INTEGER NOT NULL, " +
                    "`temperatureMax` REAL NOT NULL, " +
                    "`temperatureMin` REAL NOT NULL, " +
                    "PRIMARY KEY(`locationId`, `date`))",
            )
        }
    }

    /** v2 -> v3: выбранный город. */
    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL(
                "CREATE TABLE IF NOT EXISTS `selected_location` (" +
                    "`id` INTEGER NOT NULL, " +
                    "`locationId` TEXT NOT NULL, " +
                    "`name` TEXT NOT NULL, " +
                    "`region` TEXT, " +
                    "`latitude` REAL NOT NULL, " +
                    "`longitude` REAL NOT NULL, " +
                    "PRIMARY KEY(`id`))",
            )
        }
    }

    /** v3 -> v4: демо-таблица favorites заменена избранными городами. */
    val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL("DROP TABLE IF EXISTS `favorites`")
            connection.execSQL(
                "CREATE TABLE IF NOT EXISTS `favorite_locations` (" +
                    "`locationId` TEXT NOT NULL, " +
                    "`name` TEXT NOT NULL, " +
                    "`region` TEXT, " +
                    "`latitude` REAL NOT NULL, " +
                    "`longitude` REAL NOT NULL, " +
                    "`addedAt` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`locationId`))",
            )
        }
    }

    val all: Array<Migration> = arrayOf(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
}
