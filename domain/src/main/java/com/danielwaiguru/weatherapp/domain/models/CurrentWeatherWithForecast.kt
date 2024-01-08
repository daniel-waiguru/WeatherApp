package com.danielwaiguru.weatherapp.domain.models

data class CurrentWeatherWithForecast(
    val weather: Weather? = null,
    val forecasts: List<WeatherForecast>
)
