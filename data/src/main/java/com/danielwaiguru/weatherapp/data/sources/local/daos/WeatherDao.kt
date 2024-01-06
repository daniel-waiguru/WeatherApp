package com.danielwaiguru.weatherapp.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow

/**
 *Data access object for [WeatherEntity] entity
 */
@Dao
interface WeatherDao {
    /**
     * Save weather
     *
     * @param weather [WeatherEntity]
     */
    @Upsert
    suspend fun saveWeather(weather: WeatherEntity)

    @Query("SELECT * FROM current_weather LIMIT 1")
    fun getCurrentWeather(): Flow<WeatherEntity?>

    /**
     * Save weather forecast
     *
     * @param forecasts
     */
    @Upsert
    suspend fun saveWeatherForecast(forecasts: List<ForecastEntity>)

    /**
     * Get weather forecast
     *
     * @return returns a list of [ForecastEntity]
     */
    @Query("SELECT * FROM weather_forecast")
    fun getWeatherForecast(): Flow<List<ForecastEntity>>
}