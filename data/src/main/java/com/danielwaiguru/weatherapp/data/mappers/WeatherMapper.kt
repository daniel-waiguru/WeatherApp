package com.danielwaiguru.weatherapp.data.mappers

import com.danielwaiguru.weatherapp.data.models.dtos.CoordinatesDto
import com.danielwaiguru.weatherapp.data.models.entities.CoordinatesEntity
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import com.danielwaiguru.weatherapp.data.models.responses.ForecastResponse
import com.danielwaiguru.weatherapp.data.models.responses.WeatherDto
import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast

fun CoordinatesEntity.toCoordinates(): UserLocation = UserLocation(
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
    userLocation = coordinates.toCoordinates(),
    city = city,
    country = country,
    temp = temp,
    tempMin = tempMin,
    tempMax = tempMax,
    date = date,
    lastUpdateAt = lastUpdateAt
)

fun ForecastResponse.toForecastEntity(): List<ForecastEntity> {
    return list.map { weather ->
        val weatherInfo = weather.weather[0]
        ForecastEntity(
            icon = weatherInfo.icon,
            id = weather.id,
            date = weather.date,
            temp = weather.main.temp,
            lastUpdatedAt = System.currentTimeMillis()
        )
    }
}
fun ForecastEntity.toWeatherForecast(): WeatherForecast = WeatherForecast(
    icon = icon,
    id = id,
    date = date,
    temp = temp,
    lastUpdatedAt = lastUpdatedAt
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