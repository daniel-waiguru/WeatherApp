/*
 * MIT License
 *
 * Copyright (c) 2024 Daniel Waiguru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.danielwaiguru.weatherapp.data.mappers

import android.text.format.DateUtils
import com.danielwaiguru.weatherapp.data.models.dtos.CoordinatesDto
import com.danielwaiguru.weatherapp.data.models.dtos.WeatherDto
import com.danielwaiguru.weatherapp.data.models.entities.CoordinatesEntity
import com.danielwaiguru.weatherapp.data.models.entities.CoordinatesEntity.Companion.DEFAULT
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import com.danielwaiguru.weatherapp.data.models.responses.ForecastResponse
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.domain.utils.getDayName
import java.util.Date
import java.util.Locale

fun CoordinatesDto.toCoordinatesEntity(): CoordinatesEntity = CoordinatesEntity(
    latitude = latitude,
    longitude = longitude
)

fun WeatherEntity.toWeather(): Weather = Weather(
    description = description,
    icon = icon,
    id = id,
    userLocation = coordinates,
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
        city = name.orEmpty(),
        icon = weatherInfo.icon,
        id = id ?: 0,
        coordinates = coordinates?.toCoordinatesEntity() ?: DEFAULT,
        country = sys?.country.orEmpty(),
        conditionId = weatherInfo.id,
        temp = main.temp,
        tempMin = main.tempMin,
        tempMax = main.tempMax,
        date = date,
        lastUpdateAt = System.currentTimeMillis()
    )
}
