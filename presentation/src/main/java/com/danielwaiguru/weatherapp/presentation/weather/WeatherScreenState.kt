package com.danielwaiguru.weatherapp.presentation.weather

import com.danielwaiguru.weatherapp.domain.models.Weather

data class WeatherScreenState(
    val isLoading: Boolean = false,
    val currentWeather: Weather? = null,
    val errorMessage: String? = null
)
