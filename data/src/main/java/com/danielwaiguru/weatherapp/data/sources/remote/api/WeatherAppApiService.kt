package com.danielwaiguru.weatherapp.data.sources.remote.api

import com.danielwaiguru.weatherapp.data.models.responses.ForecastResponse
import com.danielwaiguru.weatherapp.data.models.responses.WeatherDto
import com.danielwaiguru.weatherapp.data.sources.remote.utils.ApiEndpoints.CURRENT_WEATHER_ENDPOINT
import com.danielwaiguru.weatherapp.data.sources.remote.utils.ApiEndpoints.WEATHER_FORECAST_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAppApiService {
    @GET(CURRENT_WEATHER_ENDPOINT)
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric"
    ): WeatherDto
    @GET(WEATHER_FORECAST_ENDPOINT)
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}