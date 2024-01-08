package com.danielwaiguru.weatherapp.presentation.weather

import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast

data class WeatherScreenState(
    private val isLoading: Boolean = false,
    val currentWeather: Weather? = null,
    val errorMessage: String? = null,
    val forecasts: List<WeatherForecast> = emptyList()
) {
    val isLoadingWithNoData: Boolean
        get() = isLoading && currentWeather == null
}
