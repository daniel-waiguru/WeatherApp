package com.danielwaiguru.weatherapp.data.models.dtos

data class WeatherInfoDto(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
