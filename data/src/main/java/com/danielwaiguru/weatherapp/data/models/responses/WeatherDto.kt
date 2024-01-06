package com.danielwaiguru.weatherapp.data.models.responses

import com.danielwaiguru.weatherapp.data.models.dtos.CloudsDto
import com.danielwaiguru.weatherapp.data.models.dtos.CoordinatesDto
import com.danielwaiguru.weatherapp.data.models.dtos.Main
import com.danielwaiguru.weatherapp.data.models.dtos.Rain
import com.danielwaiguru.weatherapp.data.models.dtos.Sys
import com.danielwaiguru.weatherapp.data.models.dtos.WeatherInfoDto
import com.danielwaiguru.weatherapp.data.models.dtos.Wind
import com.squareup.moshi.Json

data class WeatherDto(
    val base: String,
    val clouds: CloudsDto,
    val cod: Int,
    @field:Json(name = "coord")
    val coordinates: CoordinatesDto,
    @field:Json(name = "dt")
    val date: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherInfoDto>,
    val wind: Wind
)