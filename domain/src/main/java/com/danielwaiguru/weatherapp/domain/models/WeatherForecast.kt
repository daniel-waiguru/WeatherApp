package com.danielwaiguru.weatherapp.domain.models

data class WeatherForecast(
    val id: Int,
    val date: Long,
    val temp: Double,
    val day: String,
    val conditionId: Int
)
