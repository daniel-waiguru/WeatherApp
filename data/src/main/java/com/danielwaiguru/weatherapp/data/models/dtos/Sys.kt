package com.danielwaiguru.weatherapp.data.models.dtos

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)
