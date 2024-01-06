package com.danielwaiguru.weatherapp.data.mappers

import com.danielwaiguru.weatherapp.data.models.dtos.CoordinatesDto
import com.danielwaiguru.weatherapp.data.models.entities.CoordinatesEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import com.danielwaiguru.weatherapp.data.models.responses.WeatherDto
import com.danielwaiguru.weatherapp.domain.models.Coordinates
import com.danielwaiguru.weatherapp.domain.models.Weather

fun CoordinatesEntity.toCoordinates(): Coordinates = Coordinates(
    latitude = latitude,
    longitude = longitude
)

fun CoordinatesDto.toCoordinatesEntity(): CoordinatesEntity = CoordinatesEntity(
    latitude = lat,
    longitude = lon
)
fun WeatherEntity.toWeather(): Weather = Weather(
    description = description,
    icon = icon,
    id = id,
    coordinates = coordinates.toCoordinates(),
    city = city,
    country = country,
    temp = temp,
    tempMin = tempMin,
    tempMax = tempMax,
    date = date,
    lastUpdateAt = lastUpdateAt
)
fun WeatherDto.toWeatherEntity(): WeatherEntity {
    val weatherInfo = weather[0]
    return WeatherEntity(
        description = weatherInfo.description,
        city = name,
        icon = weatherInfo.icon,
        id = id,
        coordinates = coordinates.toCoordinatesEntity(),
        country = sys.country,
        temp = main.temp,
        tempMin = main.tempMin,
        tempMax = main.tempMax,
        date = date,
        lastUpdateAt = System.currentTimeMillis()
    )
}