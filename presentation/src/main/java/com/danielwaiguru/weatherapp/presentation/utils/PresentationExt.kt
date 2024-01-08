package com.danielwaiguru.weatherapp.presentation.utils

import com.danielwaiguru.weatherapp.presentation.models.WeatherCondition

fun Int.getWeatherCondition(): WeatherCondition {
    return when (this) {
        in 200..622 -> {
            WeatherCondition.RAINY
        }

        in 701..781 -> {
            WeatherCondition.CLOUDY
        }

        in 801..804 -> {
            WeatherCondition.CLOUDY
        }

        else -> {
            WeatherCondition.SUNNY
        }
    }
}