package com.danielwaiguru.weatherapp.domain.usecases

import com.danielwaiguru.weatherapp.domain.models.Coordinates
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

class GetCurrentWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(coordinates: Coordinates): Flow<ResultWrapper<Weather?>> =
        weatherRepository.getCurrentWeather(coordinates)
}