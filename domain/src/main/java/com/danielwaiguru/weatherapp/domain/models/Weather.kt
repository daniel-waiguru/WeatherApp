package com.danielwaiguru.weatherapp.domain.models

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val coordinates: Coordinates,
    val city: City,
    val temperature: Temperature
){
    data class City(
        val id: Int,
        val name: String,
        val country: String
    )
    data class Temperature(
        val temp: Double,
        val tempMin: Double,
        val tempMax: Double,
    )
}

