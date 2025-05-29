/*
 * MIT License
 *
 * Copyright (c) 2024 Daniel Waiguru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.danielwaiguru.weatherapp.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

    @Query("DELETE FROM weather_forecast")
    suspend fun deleteAllForecasts()

    @Query("DELETE FROM current_weather")
    suspend fun deleteAll()

    @Transaction
    suspend fun upsertCurrentWeather(weather: WeatherEntity) {
        deleteAll()
        saveWeather(weather)
    }

    @Transaction
    suspend fun upsertForecasts(forecasts: List<ForecastEntity>) {
        deleteAllForecasts()
        saveWeatherForecast(forecasts)
    }
}