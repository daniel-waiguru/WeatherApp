package com.danielwaiguru.weatherapp.domain.repositories

import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(
        userLocation: UserLocation
    ): Flow<ResultWrapper<Weather?>>

    suspend fun getWeatherForecast(
        userLocation: UserLocation
    ): Flow<ResultWrapper<List<WeatherForecast>>>
}
