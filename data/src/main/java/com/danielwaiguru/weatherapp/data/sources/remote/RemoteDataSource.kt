package com.danielwaiguru.weatherapp.data.sources.remote

import com.danielwaiguru.weatherapp.data.models.responses.ForecastResponse
import com.danielwaiguru.weatherapp.data.models.responses.WeatherResponse
import com.danielwaiguru.weatherapp.data.sources.remote.api.WeatherAppApiService
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse

    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): ForecastResponse
}

internal class RetrofitDataSource @Inject constructor(
    private val apiService: WeatherAppApiService
) : RemoteDataSource {
    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): WeatherResponse =
        apiService.getCurrentWeather(latitude, longitude)

    override suspend fun getWeatherForecast(latitude: Double, longitude: Double): ForecastResponse =
        apiService.getWeatherForecast(latitude, longitude)
}
