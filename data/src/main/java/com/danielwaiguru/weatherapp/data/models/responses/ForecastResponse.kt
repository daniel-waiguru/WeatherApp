package com.danielwaiguru.weatherapp.data.models.responses

import com.danielwaiguru.weatherapp.data.models.dtos.CityDto
import com.squareup.moshi.Json

data class ForecastResponse(
    @field:Json(name = "cod")
    val code: String,
    val message: Int,
    val list: List<WeatherDto>,
    val city: CityDto
)
