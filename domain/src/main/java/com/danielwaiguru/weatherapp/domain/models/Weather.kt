package com.danielwaiguru.weatherapp.domain.models

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val coordinates: Coordinates,
    val city: String,
    val country: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val date: Long,
    val lastUpdateAt: Long
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
