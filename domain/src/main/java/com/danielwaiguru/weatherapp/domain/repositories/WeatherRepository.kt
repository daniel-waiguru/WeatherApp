package com.danielwaiguru.weatherapp.domain.repositories

import com.danielwaiguru.weatherapp.domain.models.Coordinates
import com.danielwaiguru.weatherapp.domain.models.FiveDayWeatherForecast
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(
        coordinates: Coordinates
    ): Flow<ResultWrapper<Weather?>>

    suspend fun getWeatherForecast(
        coordinates: Coordinates
    ): Flow<ResultWrapper<List<FiveDayWeatherForecast>>>
}