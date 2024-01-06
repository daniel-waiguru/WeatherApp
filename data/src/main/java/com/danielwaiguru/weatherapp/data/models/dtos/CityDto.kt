package com.danielwaiguru.weatherapp.data.models.dtos

import com.squareup.moshi.Json

data class CityDto(
    val id: Int,
    val name: String,
    @field:Json(name = "coord")
    val coordinates: CoordinatesDto,
    val country: String,
    val population: Int
)
