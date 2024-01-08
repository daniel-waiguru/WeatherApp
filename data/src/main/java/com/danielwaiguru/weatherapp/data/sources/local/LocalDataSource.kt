package com.danielwaiguru.weatherapp.data.sources.local

import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import com.danielwaiguru.weatherapp.data.sources.local.daos.WeatherDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal interface LocalDataSource {
    suspend fun saveWeather(weatherEntity: WeatherEntity)

    fun getCurrentWeather(): Flow<WeatherEntity?>

    suspend fun saveWeatherForecast(forecasts: List<ForecastEntity>)

    fun getWeatherForecast(): Flow<List<ForecastEntity>>
}

internal class RoomDataSource @Inject constructor(
    private val weatherDao: WeatherDao
) : LocalDataSource {
    override suspend fun saveWeather(weatherEntity: WeatherEntity) {
        weatherDao.upsertCurrentWeather(weatherEntity)
    }

    override fun getCurrentWeather(): Flow<WeatherEntity?> {
        return weatherDao.getCurrentWeather()
    }

    override suspend fun saveWeatherForecast(forecasts: List<ForecastEntity>) {
        weatherDao.upsertForecasts(forecasts)
    }

    override fun getWeatherForecast(): Flow<List<ForecastEntity>> {
        return weatherDao.getWeatherForecast()
    }
}
