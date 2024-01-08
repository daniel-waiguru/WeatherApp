package com.danielwaiguru.weatherapp.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import com.danielwaiguru.weatherapp.data.sources.local.daos.WeatherDao

@Database(
    entities = [
        WeatherEntity::class,
        ForecastEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DB_NAME = "weather_app_database"
    }
}
