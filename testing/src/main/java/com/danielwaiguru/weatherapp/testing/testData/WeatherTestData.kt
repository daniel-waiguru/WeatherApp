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

package com.danielwaiguru.weatherapp.testing.testData

import com.danielwaiguru.weatherapp.data.models.dtos.CloudsDto
import com.danielwaiguru.weatherapp.data.models.dtos.CoordinatesDto
import com.danielwaiguru.weatherapp.data.models.dtos.Main
import com.danielwaiguru.weatherapp.data.models.dtos.Rain
import com.danielwaiguru.weatherapp.data.models.dtos.Sys
import com.danielwaiguru.weatherapp.data.models.dtos.WeatherDto
import com.danielwaiguru.weatherapp.data.models.dtos.WeatherInfoDto
import com.danielwaiguru.weatherapp.data.models.dtos.Wind
import com.danielwaiguru.weatherapp.data.models.entities.CoordinatesEntity
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.models.entities.WeatherEntity
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast

fun testWeather(
    id: Int = 7384,
    date: Long = 1704703010,
    lastUpdatedAt: Long = 1704703010,
    conditionId: Int = 600,
) = Weather(
    description = "reprehendunt",
    icon = "02d",
    id = id,
    userLocation = CoordinatesEntity(
        latitude = 18.19,
        longitude = 20.21,
    ),
    city = "Nairobi",
    country = "KE",
    conditionId = conditionId,
    temp = 22.23,
    tempMin = 24.25,
    tempMax = 26.27,
    date = date,
    lastUpdateAt = lastUpdatedAt,
)

fun testForecast(
    id: Int = 7384,
    date: Long = 1704703010,
    conditionId: Int = 600,
) = WeatherForecast(
    id = id,
    date = date,
    temp = 30.31,
    day = "Monday",
    conditionId = conditionId,
)

val testLocation = CoordinatesEntity(latitude = -1.286389, longitude = 36.817223)
fun testWeatherEntity() = WeatherEntity(
    description = "aptent",
    icon = "qualisque",
    id = 3692,
    coordinates = CoordinatesEntity(
        latitude = 88.89,
        longitude = 90.91,
    ),
    city = "Bon Accord",
    country = "Panama",
    conditionId = 600,
    temp = 92.93,
    tempMin = 94.95,
    tempMax = 96.97,
    date = 9056,
    lastUpdateAt = 1277,
)

fun testForecastEntity(
    id: Int? = null,
) = ForecastEntity(
    id = id,
    date = 9438,
    temp = 100.101,
    conditionId = 4127,
)
fun testWeatherDto() = WeatherDto(
    base = "interdum",
    clouds = CloudsDto(all = 4399),
    cod = 2449,
    coordinates = CoordinatesDto(
        latitude = 50.51,
        longitude = 52.53,
    ),
    date = 6847,
    id = 4047,
    main = Main(
        feelsLike = 54.55,
        groundLevel = 4451,
        humidity = 9356,
        pressure = 9532,
        seaLevel = 6552,
        temp = 56.57,
        tempMax = 58.59,
        tempMin = 60.61,
    ),
    name = "Randi Gentry",
    rain = Rain(`1h` = 62.63),
    sys = Sys(
        country = "Bulgaria",
        id = 3440,
        sunrise = 8762,
        sunset = 3023,
        type = 3296,
    ),
    timezone = 5495,
    visibility = 9731,
    weather = listOf(
        WeatherInfoDto(description = "adipiscing", icon = "eam", id = 500, main = "brute"),
    ),
    wind = Wind(
        deg = 2361,
        gust = 64.65,
        speed = 66.67,
    ),
)