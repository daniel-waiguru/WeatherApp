package com.danielwaiguru.weatherapp.testing.testData

import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast

fun testWeather(
    id: Int = 7384,
    date: Long = 1704703010,
    lastUpdatedAt: Long = 1704703010,
    conditionId: Int = 600
) = Weather(
    description = "reprehendunt",
    icon = "02d",
    id = id,
    userLocation = UserLocation(
        latitude = 18.19,
        longitude = 20.21
    ),
    city = "Nairobi",
    country = "KE",
    conditionId = conditionId,
    temp = 22.23,
    tempMin = 24.25,
    tempMax = 26.27,
    date = date,
    lastUpdateAt = lastUpdatedAt

)

fun testForecast(
    id: Int = 7384,
    date: Long = 1704703010,
    conditionId: Int = 600
) = WeatherForecast(
    id = id,
    date = date,
    temp = 30.31,
    day = "Monday",
    conditionId = conditionId
)