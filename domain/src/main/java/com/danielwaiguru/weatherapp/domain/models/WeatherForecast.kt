package com.danielwaiguru.weatherapp.domain.models

data class WeatherForecast(
    val icon: String,
    val id: Int,
    val date: Long,
    val temp: Double,
    val lastUpdatedAt: Long
)
