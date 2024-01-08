package com.danielwaiguru.weatherapp.data.mappers

import android.text.format.DateUtils
import com.danielwaiguru.weatherapp.data.models.dtos.CoordinatesDto
import com.danielwaiguru.weatherapp.data.models.entities.CoordinatesEntity
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import com.danielwaiguru.weatherapp.data.models.responses.ForecastResponse
import com.danielwaiguru.weatherapp.data.models.responses.WeatherDto
import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.domain.utils.getDayName
import java.util.Date
import java.util.Locale

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
    conditionId = conditionId,
    temp = temp,
    tempMin = tempMin,
    tempMax = tempMax,
    date = date,
    lastUpdateAt = lastUpdateAt
)

fun ForecastResponse.toForecastEntity(): List<ForecastEntity> {
    return list
        .dropWhile {
            DateUtils.isToday(Date(it.date * 1000).time)
        }
        .map { weather ->
            val weatherInfo = weather.weather[0]
            ForecastEntity(
                id = null,
                date = weather.date,
                temp = weather.main.temp,
                conditionId = weatherInfo.id
            )
        }
}

fun ForecastEntity.toWeatherForecast(): WeatherForecast = WeatherForecast(
    id = id!!,
    date = date,
    temp = temp,
    day = date.getDayName().lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
    conditionId = conditionId
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
        conditionId = weatherInfo.id,
        temp = main.temp,
        tempMin = main.tempMin,
        tempMax = main.tempMax,
        date = date,
        lastUpdateAt = System.currentTimeMillis()
    )
}
